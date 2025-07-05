# Project Structure

## Backend Module Structure

The backend follows a modular architecture organized by domain concerns:

```
src/main/java/com/ssta/quiz/
├── QuizApplication.java            # Main Spring Boot application class
├── auth/                           # Authentication related components
│   └── AuthController.java         # Authentication endpoints
├── common/                         # Shared components and utilities
│   ├── ApiResponse.java            # Standard API response format
│   └── exception/
│       └── GlobalExceptionHandler.java  # Centralized exception handling
├── config/                         # Configuration classes
│   ├── SecurityConfig.java         # Spring Security configuration
│   ├── WebConfig.java              # Web MVC configuration
│   └── WebSocketConfig.java        # WebSocket configuration
├── user/                           # User management module
│   ├── User.java                   # User entity
│   ├── UserRepository.java         # User data access
│   ├── UserService.java            # User service interface
│   ├── UserServiceImpl.java        # User service implementation
│   └── UserController.java         # User REST endpoints
├── quiz/                           # Quiz management module
│   └── Quiz.java                   # Quiz entity
└── question/                       # Question management module
    └── Question.java               # Question entity
```

## Domain-Driven Organization

The application is organized around core business domains rather than technical layers:

1. **User Domain**
    - Contains all user-related functionality: entity, repository, service, controller
    - Self-contained module with clear boundaries

2. **Quiz Domain**
    - Manages quiz definitions and lifecycle
    - Contains quiz entity and related components

3. **Question Domain**
    - Handles question creation and management
    - Contains question entity and related components

4. **Authentication Domain**
    - Handles user authentication via Twitch OAuth
    - Manages player sessions

5. **Common Components**
    - Shared utilities and cross-cutting concerns
    - Standard response formats and exception handlers

## Resource Organization

```
src/main/resources/
├── application.properties         # Main application configuration
├── application-dev.properties     # Development environment config
├── application-prod.properties    # Production environment config
├── application-test.properties    # Test environment config
└── db/
    └── migration/                 # Flyway database migrations
        └── V1__init_schema.sql    # Initial database schema
```

## Test Organization

```
src/
└── test/                          # Unit and integration tests
    ├── java/
    │   └── com/ssta/quiz/
    │       ├── user/              # User module tests
    │       ├── quiz/              # Quiz module tests
    │       └── question/          # Question module tests
    └── resources/
        └── application-test.properties
 
```

## Advantages of Domain-Driven Structure

1. **Cohesion**: Related components are grouped together, improving code organization
2. **Discoverability**: Easier to find all components related to a specific domain
3. **Modularity**: Each domain package could potentially become its own microservice
4. **Separation of Concerns**: Clear boundaries between different functional areas
5. **Reduced Coupling**: Dependencies between domains are explicit and minimized

## Dependency Management

The project uses Gradle for dependency management with the following key dependencies:

- Spring Boot (Web, Data JPA, Security, WebSocket)
- PostgreSQL driver
- Flyway for database migrations
- Lombok for reducing boilerplate code
- SpringDoc OpenAPI for API documentation

## Future Structure Considerations

This domain-driven structure facilitates future decomposition into microservices:

1. Each domain package has everything needed for its functionality
2. Clear boundaries between domains make it easier to extract services
3. Domain services communicate through well-defined interfaces

This approach maintains a cohesive development experience in the initial monolith phase while providing a clear path to
microservices if needed.

## Module Organization

The application is organized around the following core modules:

1. **Authentication Module**
    - Located in `auth/` package
    - Handles user authentication via Twitch OAuth
    - Manages player sessions

2. **Quiz Management Module**
    - Handles CRUD operations for quizzes and questions
    - Manages quiz lifecycle and state transitions

3. **Game Engine Module**
    - Controls quiz flow and timing
    - Calculates scores in real-time
    - Processes player answers

4. **Media Management Module**
    - Handles image uploads and storage
    - Serves optimized images

5. **Reporting Module**
    - Generates leaderboards and statistics
    - Archives historical quiz data
