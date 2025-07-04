# Technical Specification

## System Architecture

The Twitch Quiz Game System will follow a modular monolithic architecture, with clearly defined components that could be
separated into microservices in the future if scaling demands it.

### Core Components

1. **Authentication Module**
    - Handles quizmaster authentication via Twitch OAuth
    - Manages player sessions and nickname validation

2. **Quiz Management Module**
    - Provides CRUD operations for quizzes and questions
    - Handles question bank organization
    - Manages quiz state transitions

3. **Game Engine Module**
    - Controls quiz flow and timing
    - Calculates scores in real-time
    - Processes player answers

4. **Media Management Module**
    - Handles image uploads, processing, and storage
    - Serves optimized images for different devices

5. **Reporting Module**
    - Generates leaderboards
    - Provides quiz analytics (if implemented)
    - Archives historical quiz data

## Technology Stack

### Backend

- **Language & Framework**: Java 21 with Spring Boot 3.2.x
- **Build Tool**: Gradle
- **Key Libraries**:
    - Spring WebFlux for reactive endpoints
    - Spring Security for authentication
    - Spring Data JPA for database access
    - Hibernate Validator for input validation
    - Lombok for reducing boilerplate code
    - Springdoc-OpenAPI for API documentation
    - Spring WebSocket with STOMP for real-time communication
    - Twitch4J (or similar) for Twitch API integration
    - ImageIO or Thumbnailator for image processing
    - JUnit 5, Mockito, and TestContainers for testing

### Frontend

- **Framework**: React 18+ with TypeScript
- **Build Tool**: Vite
- **Key Libraries**:
    - React Router for navigation
    - MUI (Material-UI) for UI components
    - SockJS and STOMP.js for WebSocket communication
    - React Query for data fetching
    - React Hook Form for form handling
    - Vitest and React Testing Library for testing

### Database

- **Primary Database**: PostgreSQL 15+
- **Image Storage**: Digital Ocean Spaces (S3-compatible)
- **Cache (optional)**: Redis for high-performance data access

### DevOps

- **Hosting**: Digital Ocean Droplet (initial deployment)
- **CI/CD**: GitHub Actions
- **Containerization**: Docker
- **Monitoring**: Prometheus and Grafana (optional for initial release)

## API Design

The system will expose RESTful APIs for administrative operations and WebSocket endpoints for real-time game state
updates.

### Authentication Flow

1. Quizmaster initiates authentication via Twitch OAuth
2. System receives authorization code and exchanges for access token
3. System validates token and creates local session
4. Players authenticate with nickname only (validated for uniqueness within quiz)

### Real-Time Communication

- WebSocket connections will be established for all active quiz sessions
- STOMP protocol will be used for structured message passing
- Topics will be segregated by quiz ID for proper isolation

## Performance Considerations

- Target response time for API calls: < 200ms
- Target WebSocket message delivery: < 100ms
- Support for at least 50 concurrent players per quiz
- Image optimization to reduce bandwidth usage
- Database indexing strategy for common query patterns

## Security Measures

- HTTPS for all communications
- OAuth 2.0 for quizmaster authentication
- CSRF protection for form submissions
- Input validation and sanitization
- Rate limiting for public endpoints
- Secure cookie configuration

## Scalability Path

Initially deployed as a monolith, the system is designed with clear component boundaries to facilitate future
decomposition into microservices if needed:

1. Separate services for Authentication, Quiz Management, Game Engine, and Reporting
2. Message queue introduction for asynchronous processing
3. Horizontal scaling of game engine instances
4. Database sharding by quiz ID

## Browser Compatibility

- Chrome, Firefox, Safari, Edge (latest two major versions)
- Mobile browsers on iOS 14+ and Android 10+
- Responsive design for desktop, tablet, and mobile devices
