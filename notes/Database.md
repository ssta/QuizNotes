# Database Schema

## Overview

The Twitch Quiz Game System uses PostgreSQL as its primary database. The schema is designed to efficiently support quiz
creation, gameplay, and historical reporting while maintaining data integrity through appropriate constraints and
relationships.

## Entity Relationship Diagram

```
+---------------+       +---------------+       +---------------+
| Quizmaster    |       | Quiz          |       | Question      |
+---------------+       +---------------+       +---------------+
| PK id         |<----->| PK id         |<----->| PK id         |
| username      |       | FK quizmaster_id      | FK quiz_id     |
| twitch_id     |       | title         |       | text          |
| email         |       | status        |       | image_url     |
| created_at    |       | created_at    |       | options       |
| last_login    |       | started_at    |       | correct_option|
+---------------+       | ended_at      |       | sequence_num  |
                        +---------------+       | created_at    |
                                |               +---------------+
                                v                       ^
                        +---------------+               |
                        | Player        |               |
                        +---------------+               |
                        | PK id         |               |
                        | FK quiz_id    |<--------------+
                        | nickname      |               |
                        | joined_at     |               |
                        | last_active   |               |
                        +---------------+               |
                                |                       |
                                v                       |
                        +---------------+               |
                        | Answer        |               |
                        +---------------+               |
                        | PK id         |               |
                        | FK player_id  |               |
                        | FK question_id|---------------+
                        | selected_option
                        | answered_at   |
                        | score         |
                        +---------------+
```

## Tables

### Quizmaster

```sql
CREATE TABLE quizmaster (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    twitch_id VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    CONSTRAINT valid_email CHECK (email IS NULL OR email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);
```

### Quiz

```sql
CREATE TABLE quiz (
    id SERIAL PRIMARY KEY,
    quizmaster_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    profanity_filter BOOLEAN NOT NULL DEFAULT TRUE,
    is_practice BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (quizmaster_id) REFERENCES quizmaster(id) ON DELETE CASCADE,
    CONSTRAINT valid_status CHECK (status IN ('DRAFT', 'WAITING', 'ACTIVE', 'PAUSED', 'COMPLETED'))
);
```

### Question

```sql
CREATE TABLE question (
    id SERIAL PRIMARY KEY,
    quiz_id INTEGER NOT NULL,
    text TEXT NOT NULL,
    image_url VARCHAR(255),
    options JSONB NOT NULL,
    correct_option INTEGER NOT NULL,
    sequence_num INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE,
    CONSTRAINT valid_correct_option CHECK (correct_option >= 0),
    CONSTRAINT valid_sequence_num CHECK (sequence_num >= 0),
    CONSTRAINT unique_sequence_in_quiz UNIQUE (quiz_id, sequence_num)
);
```

The `options` column uses JSONB to store an array of option texts:

```json
[
  "Option A text",
  "Option B text",
  "Option C text",
  "Option D text"
]
```

### Player

```sql
CREATE TABLE player (
    id SERIAL PRIMARY KEY,
    quiz_id INTEGER NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_active TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE,
    CONSTRAINT unique_nickname_per_quiz UNIQUE (quiz_id, nickname)
);
```

### Answer

```sql
CREATE TABLE answer (
    id SERIAL PRIMARY KEY,
    player_id INTEGER NOT NULL,
    question_id INTEGER NOT NULL,
    selected_option INTEGER,
    answered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    score INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE,
    CONSTRAINT unique_player_question UNIQUE (player_id, question_id)
);
```

### Question Bank (Optional Enhancement)

```sql
CREATE TABLE question_bank (
    id SERIAL PRIMARY KEY,
    quizmaster_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quizmaster_id) REFERENCES quizmaster(id) ON DELETE CASCADE,
    CONSTRAINT unique_bank_name_per_quizmaster UNIQUE (quizmaster_id, name)
);

CREATE TABLE bank_question (
    id SERIAL PRIMARY KEY,
    bank_id INTEGER NOT NULL,
    text TEXT NOT NULL,
    image_url VARCHAR(255),
    options JSONB NOT NULL,
    correct_option INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bank_id) REFERENCES question_bank(id) ON DELETE CASCADE
);
```

## Indexes

```sql
-- For quick quiz lookup by quizmaster
CREATE INDEX idx_quiz_quizmaster ON quiz(quizmaster_id);

-- For quick question lookup by quiz
CREATE INDEX idx_question_quiz ON question(quiz_id);

-- For quick player lookup by quiz
CREATE INDEX idx_player_quiz ON player(quiz_id);

-- For score calculations and leaderboards
CREATE INDEX idx_answer_player ON answer(player_id);
CREATE INDEX idx_answer_question ON answer(question_id);

-- For time-based queries
CREATE INDEX idx_player_last_active ON player(last_active);
CREATE INDEX idx_answer_answered_at ON answer(answered_at);
```

## Data Constraints

1. A quiz must be associated with exactly one quizmaster
2. A question must belong to exactly one quiz
3. A player must be associated with exactly one quiz
4. A player can answer each question only once
5. Player nicknames must be unique within a quiz
6. Question sequence numbers must be unique within a quiz

## Views

```sql
-- Current quiz scores
CREATE VIEW quiz_scores AS
SELECT 
    p.quiz_id,
    p.id as player_id,
    p.nickname,
    COALESCE(SUM(a.score), 0) as total_score,
    COUNT(a.id) as questions_answered,
    MAX(a.answered_at) as last_answer_time
FROM 
    player p
LEFT JOIN 
    answer a ON p.id = a.player_id
GROUP BY 
    p.quiz_id, p.id, p.nickname
ORDER BY 
    total_score DESC;

-- Question statistics
CREATE VIEW question_stats AS
SELECT 
    q.id as question_id,
    q.quiz_id,
    q.text,
    COUNT(a.id) as total_answers,
    SUM(CASE WHEN a.selected_option = q.correct_option THEN 1 ELSE 0 END) as correct_answers,
    AVG(EXTRACT(EPOCH FROM (a.answered_at - q.created_at))) as avg_answer_time_seconds
FROM 
    question q
LEFT JOIN 
    answer a ON q.id = a.question_id
GROUP BY 
    q.id, q.quiz_id, q.text;
```

## Database Migrations

Database schema changes will be managed through Flyway migrations, allowing for versioned evolution of the schema while
preserving data integrity.
