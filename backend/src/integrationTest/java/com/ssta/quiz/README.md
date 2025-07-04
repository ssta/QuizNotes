# Integration Tests

This directory contains integration tests that verify the application works correctly with external dependencies.

## Database Integration Tests

The database integration tests ensure:

1. The database exists and is accessible
2. The application can connect to the database
3. The database contains the correct schema for the application

## Running Database Tests

To run the database integration tests, use:

```bash
./gradlew :backend:databaseTest
```

These tests are tagged with `@Tag("databaseTest")` and will not run during the normal test cycle with
`./gradlew :backend:test`.

## Test Configuration

The tests use the `application-test.properties` file in this directory, which activates the `test` profile.

For database tests to run successfully:

1. Ensure your PostgreSQL database is running
2. The database 'QUIZPOC' exists
3. The user credentials in the test properties file are correct
