# Database Configuration

## Overview

The Quiz POC application uses PostgreSQL as its primary database. The database schema is managed through Flyway
migrations, ensuring consistent and repeatable database setup across all environments.

## Setup Options

### Option 1: Docker Compose (Recommended for Development)

The easiest way to set up the database for development is using the provided Docker Compose file:

```bash
cd backend
docker-compose up -d
```

This will start a PostgreSQL container with the following configuration:

- Database name: QUIZPOC
- Username: postgres
- Password: 0Password1
- Port: 5432 (default)

### Option 2: Manual PostgreSQL Setup

If you prefer to use an existing PostgreSQL installation:

1. Create a database named `QUIZPOC`:
   ```sql
   CREATE DATABASE "QUIZPOC";
   ```

2. Ensure your PostgreSQL user has appropriate permissions to this database

3. Update `application.properties` with your database connection details if different from the defaults

## Database Migration

Database migrations are managed using Flyway. When the application starts, it automatically applies any pending
migrations.

Migration scripts are located in `src/main/resources/db/migration` and follow the naming convention:

```
V{version}__{description}.sql
```

For example: `V1__create_initial_schema.sql`

## Verifying Database Setup

You can verify that your database is correctly configured by running the database integration tests:

```bash
./gradlew :backend:databaseTest
```

These tests check:

1. Database connectivity
2. Presence of required tables
3. Current migration version

## Common Database Operations

### Connecting via psql

```bash
psql -U postgres -d QUIZPOC
```

### Viewing Current Schema

```sql
\dt
```

### Resetting the Database

In development, you may want to reset the database to a clean state:

```bash
# Using Docker Compose
docker-compose down -v
docker-compose up -d

# Using psql
drop database "QUIZPOC";
create database "QUIZPOC";
```

## Production Considerations

For production environments:

1. Use strong, unique passwords
2. Implement proper database backup procedures
3. Consider using a managed PostgreSQL service
4. Enable SSL connections
5. Set up appropriate connection pooling

These settings can be configured in the `application-prod.properties` file.
