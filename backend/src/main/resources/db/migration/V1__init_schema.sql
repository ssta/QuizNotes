/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

-- Initial schema setup

-- Users table for quiz masters
CREATE TABLE users
(
    id BIGINT PRIMARY KEY,
    username   VARCHAR(50) NOT NULL UNIQUE,
    twitch_id  VARCHAR(50) UNIQUE,
    email      VARCHAR(100) UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Quizzes table
CREATE TABLE quizzes
(
    id      BIGINT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    user_id BIGINT NOT NULL REFERENCES users (id),
    status      VARCHAR(20)  NOT NULL    DEFAULT 'DRAFT', -- DRAFT, ACTIVE, COMPLETED, ARCHIVED
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Questions table
CREATE TABLE questions
(
    id      BIGINT PRIMARY KEY,
    quiz_id BIGINT NOT NULL REFERENCES quizzes (id),
    question_text TEXT    NOT NULL,
    image_url     VARCHAR(255),
    time_limit    INTEGER NOT NULL         DEFAULT 20,
    order_index   INTEGER NOT NULL,
    options JSONB,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Answer options table
CREATE TABLE answer_options
(
    id          BIGINT PRIMARY KEY,
    question_id BIGINT NOT NULL REFERENCES questions (id),
    option_text TEXT    NOT NULL,
    is_correct  BOOLEAN NOT NULL         DEFAULT FALSE,
    order_index INTEGER NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Players table
CREATE TABLE players
(
    id      BIGINT PRIMARY KEY,
    nickname   VARCHAR(50)  NOT NULL,
    quiz_id BIGINT NOT NULL REFERENCES quizzes (id),
    session_id VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (quiz_id, nickname)
);

-- Player answers table
CREATE TABLE player_answers
(
    id               BIGINT PRIMARY KEY,
    player_id        BIGINT NOT NULL REFERENCES players (id),
    question_id      BIGINT NOT NULL REFERENCES questions (id),
    answer_option_id BIGINT REFERENCES answer_options (id),
    response_time_ms INTEGER,
    score            INTEGER,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (player_id, question_id)
);

-- Indices for better performance
CREATE INDEX idx_quizzes_user_id ON quizzes (user_id);
CREATE INDEX idx_questions_quiz_id ON questions (quiz_id);
CREATE INDEX idx_answer_options_question_id ON answer_options (question_id);
CREATE INDEX idx_players_quiz_id ON players (quiz_id);
CREATE INDEX idx_player_answers_player_id ON player_answers (player_id);
CREATE INDEX idx_player_answers_question_id ON player_answers (question_id);
