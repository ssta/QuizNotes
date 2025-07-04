# Quiz POC Backend

This is the backend service for the Twitch Quiz Game System.

## Technology Stack

- Java 21
- Spring Boot 3.2.x
- PostgreSQL for data storage
- Flyway for database migrations
- Spring Security for authentication
- Spring WebSocket for real-time communication

## Getting Started

### Prerequisites

- JDK 21
- PostgreSQL (local instance or Docker)

### Database Setup

The application requires a PostgreSQL database. You have two options:

1. **Using Docker Compose (Recommended)**:
   ```bash
   cd backend
   docker-compose up -d
   ```

2. **Manual Setup**:
    - Create a PostgreSQL database named "QUIZPOC"
    - Configure the connection in `application.properties`

For detailed database information, see [DATABASE.md](DATABASE.md)

### Running the Application

1. Ensure PostgreSQL is running with a database named "QUIZPOC"
2. Run the application using Gradle:

```bash
./gradlew :backend:bootRun
```

### API Documentation

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

### Database Migrations

Database migrations are managed by Flyway and will run automatically on application startup.

## Project Structure

- `src/main/java/com/ssta/quiz` - Main application code
    - `config` - Configuration classes
    - `controller` - REST endpoints
    - `model` - Domain entities
    - `repository` - Data access layer
    - `service` - Business logic
    - `dto` - Data transfer objects
    - `exception` - Custom exceptions
    - `util` - Utility classes

- `src/main/resources` - Application resources
    - `db/migration` - Database migration scripts
    - `application.properties` - Application configuration

- `src/test` - Test code

## Development Guidelines

- Follow the Spring Boot best practices
- Write unit tests for all business logic
- Use DTOs to transfer data between layers
- Document all public APIs using Swagger annotations
