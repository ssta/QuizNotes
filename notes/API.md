# API Specification

## Overview

The Twitch Quiz Game System API is divided into two main components:

1. **RESTful API**: For administrative operations, quiz management, and authentication
2. **WebSocket API**: For real-time game state updates and player interactions

All API endpoints are secured with appropriate authentication mechanisms and follow RESTful design principles.

## Base URLs

- **REST API**: `https://{domain}/api/v1`
- **WebSocket**: `wss://{domain}/ws`

## Authentication

### Quizmaster Authentication

#### Twitch OAuth Flow

```
GET /auth/twitch/login
```

Redirects to Twitch OAuth page

```
GET /auth/twitch/callback
```

Handles OAuth callback from Twitch

#### Session Management

```
GET /auth/session
```

Returns current session information

```
POST /auth/logout
```

Ends the current session

### Player Authentication

```
POST /player/join
```

Joins a quiz with a nickname

**Request:**

```json
{
  "quizCode": "ABC123",
  "nickname": "PlayerName"
}
```

**Response:**

```json
{
  "playerId": "p12345",
  "nickname": "PlayerName",
  "token": "player-session-token",
  "quizId": "q789",
  "joinedAt": "2025-07-02T15:30:45Z"
}
```

## RESTful API Endpoints

### Quiz Management

#### Get Quizzes

```
GET /quizzes
```

Returns all quizzes for the authenticated quizmaster

**Response:**

```json
{
  "quizzes": [
    {
      "id": "q123",
      "title": "Science Quiz",
      "status": "DRAFT",
      "questionCount": 10,
      "createdAt": "2025-07-01T10:15:30Z",
      "lastModified": "2025-07-01T14:20:10Z"
    },
    {
      "id": "q456",
      "title": "History Quiz",
      "status": "COMPLETED",
      "questionCount": 15,
      "createdAt": "2025-06-28T09:00:00Z",
      "lastModified": "2025-06-28T11:30:45Z",
      "startedAt": "2025-06-28T19:00:00Z",
      "endedAt": "2025-06-28T20:15:30Z"
    }
  ]
}
```

#### Create Quiz

```
POST /quizzes
```

Creates a new quiz

**Request:**

```json
{
  "title": "Geography Quiz",
  "profanityFilter": true,
  "isPractice": false
}
```

**Response:**

```json
{
  "id": "q789",
  "title": "Geography Quiz",
  "status": "DRAFT",
  "questionCount": 0,
  "createdAt": "2025-07-02T15:30:45Z",
  "lastModified": "2025-07-02T15:30:45Z",
  "code": "ABC123",
  "profanityFilter": true,
  "isPractice": false
}
```

#### Get Quiz Details

```
GET /quizzes/{quizId}
```

Returns detailed information about a specific quiz

**Response:**

```json
{
  "id": "q789",
  "title": "Geography Quiz",
  "status": "DRAFT",
  "questions": [
    {
      "id": "que123",
      "text": "What is the capital of France?",
      "imageUrl": "https://example.com/images/paris.jpg",
      "options": ["Paris", "London", "Berlin", "Madrid"],
      "correctOption": 0,
      "sequenceNum": 0
    }
  ],
  "createdAt": "2025-07-02T15:30:45Z",
  "lastModified": "2025-07-02T15:35:20Z",
  "code": "ABC123",
  "profanityFilter": true,
  "isPractice": false
}
```

#### Update Quiz

```
PUT /quizzes/{quizId}
```

Updates quiz details

**Request:**

```json
{
  "title": "World Geography Quiz",
  "profanityFilter": false
}
```

**Response:**

```json
{
  "id": "q789",
  "title": "World Geography Quiz",
  "status": "DRAFT",
  "questionCount": 1,
  "lastModified": "2025-07-02T15:40:10Z",
  "profanityFilter": false,
  "isPractice": false
}
```

#### Delete Quiz

```
DELETE /quizzes/{quizId}
```

**Response:** HTTP 204 No Content

### Question Management

#### Add Question

```
POST /quizzes/{quizId}/questions
```

**Request:**

```json
{
  "text": "Which is the largest ocean on Earth?",
  "options": ["Atlantic", "Indian", "Pacific", "Arctic"],
  "correctOption": 2,
  "sequenceNum": 1
}
```

**Response:**

```json
{
  "id": "que456",
  "quizId": "q789",
  "text": "Which is the largest ocean on Earth?",
  "imageUrl": null,
  "options": ["Atlantic", "Indian", "Pacific", "Arctic"],
  "correctOption": 2,
  "sequenceNum": 1,
  "createdAt": "2025-07-02T15:45:30Z"
}
```

#### Update Question

```
PUT /quizzes/{quizId}/questions/{questionId}
```

**Request:**

```json
{
  "text": "Which is the largest ocean on Earth by surface area?",
  "options": ["Atlantic", "Indian", "Pacific", "Arctic"],
  "correctOption": 2,
  "sequenceNum": 1
}
```

**Response:**

```json
{
  "id": "que456",
  "quizId": "q789",
  "text": "Which is the largest ocean on Earth by surface area?",
  "imageUrl": null,
  "options": ["Atlantic", "Indian", "Pacific", "Arctic"],
  "correctOption": 2,
  "sequenceNum": 1,
  "lastModified": "2025-07-02T15:50:15Z"
}
```

#### Delete Question

```
DELETE /quizzes/{quizId}/questions/{questionId}
```

**Response:** HTTP 204 No Content

#### Upload Question Image

```
POST /quizzes/{quizId}/questions/{questionId}/image
```

**Request:** Multipart form data with image file

**Response:**

```json
{
  "id": "que456",
  "imageUrl": "https://storage.example.com/quiz-images/que456-image.jpg"
}
```

### Quiz Flow Control

#### Start Quiz

```
POST /quizzes/{quizId}/start
```

**Response:**

```json
{
  "id": "q789",
  "status": "WAITING",
  "startedAt": "2025-07-02T16:00:00Z"
}
```

#### Next Question

```
POST /quizzes/{quizId}/next-question
```

**Response:**

```json
{
  "id": "q789",
  "status": "ACTIVE",
  "currentQuestionId": "que123",
  "currentQuestionIndex": 0,
  "questionStartedAt": "2025-07-02T16:05:30Z"
}
```

#### End Quiz

```
POST /quizzes/{quizId}/end
```

**Response:**

```json
{
  "id": "q789",
  "status": "COMPLETED",
  "endedAt": "2025-07-02T16:30:45Z"
}
```

### Results and Statistics

#### Get Quiz Results

```
GET /quizzes/{quizId}/results
```

**Response:**

```json
{
  "quizId": "q789",
  "title": "World Geography Quiz",
  "status": "COMPLETED",
  "playerCount": 15,
  "questionCount": 10,
  "startedAt": "2025-07-02T16:00:00Z",
  "endedAt": "2025-07-02T16:30:45Z",
  "topScores": [
    {
      "playerId": "p12345",
      "nickname": "GeoWiz",
      "score": 87500,
      "rank": 1
    },
    {
      "playerId": "p23456",
      "nickname": "MapMaster",
      "score": 75200,
      "rank": 2
    },
    {
      "playerId": "p34567",
      "nickname": "Traveler",
      "score": 68900,
      "rank": 3
    }
  ]
}
```

#### Get Question Statistics

```
GET /quizzes/{quizId}/questions/{questionId}/stats
```

**Response:**

```json
{
  "questionId": "que123",
  "text": "What is the capital of France?",
  "totalAnswers": 15,
  "correctAnswers": 12,
  "incorrectAnswers": 3,
  "averageAnswerTimeMs": 5240,
  "optionCounts": [12, 2, 1, 0],
  "fastestCorrectAnswerMs": 1850
}
```

## WebSocket API

### Connection

Clients connect to the WebSocket endpoint with their session token:

```
wss://{domain}/ws?token={session-token}
```

### Topics

The system uses STOMP protocol over WebSockets with the following topics:

#### Quizmaster Topics

```
/topic/quiz/{quizId}/admin/players
```

Updates on player joins/leaves

```
/topic/quiz/{quizId}/admin/answers
```

Real-time answer submissions

```
/topic/quiz/{quizId}/admin/stats
```

Real-time question statistics

#### Player Topics

```
/topic/quiz/{quizId}/state
```

Quiz state updates (waiting, active, paused, completed)

```
/topic/quiz/{quizId}/question
```

Current question updates

```
/topic/quiz/{quizId}/scores
```

Leaderboard updates

```
/topic/quiz/{quizId}/result
```

Individual answer result feedback

### Message Formats

#### Quiz State Update

```json
{
  "quizId": "q789",
  "state": "ACTIVE",
  "currentQuestionIndex": 2,
  "totalQuestions": 10,
  "timestamp": "2025-07-02T16:10:15Z"
}
```

#### Current Question

```json
{
  "questionId": "que456",
  "text": "Which is the largest ocean on Earth?",
  "imageUrl": null,
  "options": ["Atlantic", "Indian", "Pacific", "Arctic"],
  "timeLimit": 20,
  "startedAt": "2025-07-02T16:10:15Z"
}
```

#### Answer Submission

```json
{
  "questionId": "que456",
  "selectedOption": 2,
  "timestamp": "2025-07-02T16:10:22Z"
}
```

#### Answer Result

```json
{
  "questionId": "que456",
  "correct": true,
  "selectedOption": 2,
  "correctOption": 2,
  "pointsEarned": 8750,
  "answerTimeMs": 7000,
  "newTotalScore": 22500
}
```

#### Leaderboard Update

```json
{
  "scores": [
    { "playerId": "p12345", "nickname": "GeoWiz", "score": 22500, "rank": 1 },
    { "playerId": "p23456", "nickname": "MapMaster", "score": 18750, "rank": 2 },
    { "playerId": "p34567", "nickname": "Traveler", "score": 15000, "rank": 3 }
  ],
  "playerCount": 15,
  "timestamp": "2025-07-02T16:10:30Z"
}
```

## Error Handling

All API endpoints return standard HTTP status codes with error details in the response body:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid question format",
  "timestamp": "2025-07-02T15:45:30Z",
  "path": "/api/v1/quizzes/q789/questions"
}
```

## Rate Limiting

API endpoints are rate limited to prevent abuse:

- Authentication endpoints: 10 requests per minute
- Quiz management endpoints: 60 requests per minute
- Player join endpoint: 30 requests per minute

Rate limit headers are included in responses:

```
X-RateLimit-Limit: 60
X-RateLimit-Remaining: 58
X-RateLimit-Reset: 1688313030
```

## API Versioning

The API uses URI versioning (v1) to allow for future changes while maintaining backward compatibility. All endpoints are
prefixed with `/api/v1/`.

## Documentation

The API will be documented using OpenAPI (Swagger) specification, accessible at:

```
https://{domain}/api/docs
```
