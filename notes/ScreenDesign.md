# Screen Designs

## Overview

This document outlines the key screens for both the quizmaster and player interfaces. Each screen is described with its
purpose, key components, and user interactions.

## Quizmaster Interface

### 1. Login Screen

```
+--------------------------------------------------+
|                                                  |
|                  QUIZ MASTER                     |
|                                                  |
|                                                  |
|                                                  |
|                                                  |
|                                                  |
|                                                  |
|        +------------------------------+          |
|        |      Login with Twitch       |          |
|        +------------------------------+          |
|                                                  |
|                                                  |
|                                                  |
|                                                  |
+--------------------------------------------------+
```

**Key Components:**

- Twitch OAuth login button
- Application logo and branding
- Brief description of the system

### 2. Dashboard

```
+--------------------------------------------------+
| Logo   Dashboard | Quizzes | Settings   User ▼   |
+--------------------------------------------------+
|                                                  |
| Welcome, [Username]                              |
|                                                  |
| +----------------+      +-------------------+    |
| | Active Quizzes |      | Recent Quizzes   |    |
| +----------------+      +-------------------+    |
| | None           |      | Quiz 1 (2 days ago)  | |
| |                |      | Quiz 2 (5 days ago)  | |
| |                |      | Quiz 3 (1 week ago)  | |
| +----------------+      +-------------------+    |
|                                                  |
| +----------------+                               |
| | Create New Quiz|                               |
| +----------------+                               |
|                                                  |
+--------------------------------------------------+
```

**Key Components:**

- Navigation menu
- Active quizzes section
- Recent quizzes history
- Create new quiz button
- User profile dropdown

### 3. Quiz Creation

```
+--------------------------------------------------+
| Logo   Dashboard | Quizzes | Settings   User ▼   |
+--------------------------------------------------+
| < Back to Dashboard                              |
|                                                  |
| Create New Quiz                                  |
| +--------------------------------------------------+
| | Quiz Title: [                              ]    |
| |                                                 |
| | Description: [                                 ]|
| |             [                                 ] |
| |                                                 |
| | Settings:                                       |
| | [ ] Enable profanity filter                     |
| | [ ] Practice mode                               |
| |                                                 |
| +--------------------------------------------------+
|                                                  |
| +----------------+                               |
| | Create & Edit  |                               |
| +----------------+                               |
+--------------------------------------------------+
```

**Key Components:**

- Quiz details form
- Settings toggles
- Back navigation
- Create & Edit button to proceed to question editing

### 4. Question Editor

```
+--------------------------------------------------+
| Logo   Dashboard | Quizzes | Settings   User ▼   |
+--------------------------------------------------+
| < Back to Quizzes       Quiz: [Quiz Title]       |
+--------------------------------------------------+
| +-------------+  |  +---------------------------+|
| | Questions   |  |  | Question Text:           ||
| +-------------+  |  | [                       ] ||
| | 1. First Q  |  |  | [                       ] ||
| | 2. Second Q |  |  +---------------------------+|
| | + Add New   |  |  | Image (optional):        ||
| |             |  |  | [Choose File] [Remove]   ||
| |             |  |  | Preview:                 ||
| |             |  |  | [Image preview area]     ||
| |             |  |  +---------------------------+|
| |             |  |  | Answer Options:          ||
| |             |  |  | A. [                   ] ||
| |             |  |  | B. [                   ] ||
| |             |  |  | C. [                   ] ||
| |             |  |  | D. [                   ] ||
| |             |  |  |                         ||
| |             |  |  | Correct: (O) A (O) B (O) C (O) D ||
| +-------------+  |  +---------------------------+|
|                  |  | [Save Question] [Delete] ||
+--------------------------------------------------+
| [Save Quiz]      [Preview Quiz]     [Start Quiz] |
+--------------------------------------------------+
```

**Key Components:**

- Question list sidebar
- Question editing form
- Image upload functionality
- Multiple choice options editor
- Correct answer selector
- Save, preview, and start quiz actions

### 5. Quiz Control Panel

```
+--------------------------------------------------+
| Logo                       Quiz: [Quiz Title]    |
+--------------------------------------------------+
| +----------------+  |  +----------------------+  |
| | Current Status |  |  | Current Question    |  |
| +----------------+  |  +----------------------+  |
| | WAITING ROOM   |  |  | Question #1 of 10   |  |
| |                |  |  |                      |  |
| | Players: 12    |  |  | What is the capital |  |
| |                |  |  | of France?          |  |
| | Start Quiz     |  |  |                      |  |
| +----------------+  |  | [Image if present]   |  |
| | Quiz Timer:     | |  |                      |  |
| | 00:00          |  |  | A. Paris            |  |
| +----------------+  |  | B. London           |  |
| | Controls:      |  |  | C. Berlin           |  |
| | [Next Question]|  |  | D. Madrid           |  |
| | [Pause Quiz]   |  |  |                      |  |
| | [End Quiz]     |  |  | Correct: A          |  |
| +----------------+  |  +----------------------+  |
+--------------------------------------------------+
| +--------------------------------------------------+
| | Leaderboard                                   ▼ |
| | 1. Player1 - 25,000                             |
| | 2. Player2 - 23,400                             |
| | 3. Player3 - 18,750                             |
| +--------------------------------------------------+
```

**Key Components:**

- Quiz status display
- Current player count
- Quiz timer
- Question navigation controls
- Current question display
- Real-time leaderboard

## Player Interface

### 1. Join Quiz

```
+--------------------------------------------------+
|                                                  |
|                  QUIZ GAME                       |
|                                                  |
|                                                  |
| Enter Quiz Code:                                 |
| +------------------------+                        |
| |         ABC123         |                       |
| +------------------------+                        |
|                                                  |
| Choose a Nickname:                               |
| +------------------------+                        |
| |         [         ]    |                       |
| +------------------------+                        |
|                                                  |
| +------------------------+                        |
| |         Join Quiz      |                       |
| +------------------------+                        |
|                                                  |
+--------------------------------------------------+
```

**Key Components:**

- Quiz code input
- Nickname input field
- Join button
- Application branding

### 2. Waiting Room

```
+--------------------------------------------------+
|                                                  |
|                  QUIZ GAME                       |
|                                                  |
| +------------------------+                        |
| |    Waiting for quiz    |                       |
| |        to start        |                       |
| +------------------------+                        |
|                                                  |
| Players in lobby: 14                             |
|                                                  |
| +------------------------+                        |
| | • Player1             |                        |
| | • Player2             |                        |
| | • Player3             |                        |
| | • You (Player4)       |                        |
| | • Player5             |                        |
| | • Player6             |                        |
| +------------------------+                        |
|                                                  |
| The quiz will begin when the quizmaster          |
| is ready. Stay tuned!                            |
|                                                  |
+--------------------------------------------------+
```

**Key Components:**

- Status message
- Player count
- Player list
- User identifier in the list

### 3. Question Screen

```
+--------------------------------------------------+
| Score: 15,400                     Timer: 0:15    |
+--------------------------------------------------+
|                                                  |
| Question 3 of 10                                 |
|                                                  |
| Which planet is known as the Red Planet?         |
|                                                  |
| [Image if present]                               |
|                                                  |
| +------------------------+                        |
| |      A. Venus          |                       |
| +------------------------+                        |
|                                                  |
| +------------------------+                        |
| |      B. Mars           |                       |
| +------------------------+                        |
|                                                  |
| +------------------------+                        |
| |      C. Jupiter        |                       |
| +------------------------+                        |
|                                                  |
| +------------------------+                        |
| |      D. Saturn         |                       |
| +------------------------+                        |
|                                                  |
+--------------------------------------------------+
```

**Key Components:**

- Current score display
- Timer countdown
- Question text and optional image
- Multiple choice options as buttons
- Question progress indicator

### 4. Answer Feedback

```
+--------------------------------------------------+
| Score: 18,250                                    |
+--------------------------------------------------+
|                                                  |
| Your answer: B. Mars                             |
|                                                  |
| +------------------------+                        |
| |         CORRECT!       |                       |
| +------------------------+                        |
|                                                  |
| +------------------------+                        |
| |   You earned 2,850     |                       |
| |        points!         |                       |
| +------------------------+                        |
|                                                  |
| Waiting for the next question...                 |
|                                                  |
| Current Rankings:                                |
| 1. Player2 - 23,400                              |
| 2. YOU - 18,250                                  |
| 3. Player5 - 16,750                              |
|                                                  |
+--------------------------------------------------+
```

**Key Components:**

- Answer feedback (correct/incorrect)
- Points earned/lost display
- Updated score
- Waiting message
- Current top rankings

### 5. Final Results

```
+--------------------------------------------------+
|                                                  |
|                  QUIZ RESULTS                    |
|                                                  |
| +------------------------+                        |
| |     Quiz Complete!     |                       |
| +------------------------+                        |
|                                                  |
| Your final score: 32,750                         |
| Your position: 2nd of 14 players                 |
|                                                  |
| +------------------------------------------------+
| | Final Leaderboard                            ▼ |
| | 1. Player2 - 35,400  🏆                        |
| | 2. YOU - 32,750  🥈                            |
| | 3. Player5 - 29,750  🥉                        |
| | 4. Player1 - 25,000                            |
| | 5. Player7 - 22,400                            |
| +------------------------------------------------+
|                                                  |
| +------------------------+                        |
| |    Return to Lobby     |                       |
| +------------------------+                        |
|                                                  |
+--------------------------------------------------+
```

**Key Components:**

- Quiz completion message
- Final score display
- Player ranking
- Complete leaderboard
- Return to lobby option

## Mobile Adaptations

All interfaces will adapt to mobile screens, with these key changes:

1. **Quizmaster Interface**:
    - Navigation converts to hamburger menu
    - Panels stack vertically instead of side-by-side
    - Touch-optimized controls

2. **Player Interface**:
    - Full-width buttons
    - Reduced padding
    - Optimized for thumb-reach zones
    - Collapsible sections

## Responsive Breakpoints

- **Mobile**: < 600px
- **Tablet**: 600px - 960px
- **Desktop**: > 960px

## Accessibility Considerations

All screens incorporate:

- Sufficient color contrast
- Focus indicators for keyboard navigation
- Text alternatives for images
- Semantic HTML structure
- Touch targets of appropriate size
- Screen reader-friendly content
