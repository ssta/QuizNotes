package com.ssta.quiz.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Tag("databaseTest")
public class DatabaseConnectionIT {

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void testDatabaseConnection() {
    assertNotNull(entityManager, "EntityManager should not be null");

    // Verify database connection works
    Query query = entityManager.createNativeQuery("SELECT 1");
    List<?> result = query.getResultList();
    assertNotNull(result, "Query result should not be null");
    assertTrue(result.size() > 0, "Query should return at least one result");
  }

  @Test
  public void testSchemaExists() {
    // Query to check if important tables exist
    String sql = "SELECT EXISTS (" +
        "SELECT FROM information_schema.tables " +
        "WHERE table_schema = 'public' " +
        "AND table_name = 'flyway_schema_history')";

    Query query = entityManager.createNativeQuery(sql);
    boolean flywayTableExists = (boolean) query.getSingleResult();

    assertTrue(flywayTableExists, "Flyway schema history table should exist");
  }

  @Test
  public void testDatabaseVersion() {
    // Check Flyway migration version to ensure database is at the expected version
    String sql = "SELECT MAX(version) FROM flyway_schema_history WHERE success = true";
    Query query = entityManager.createNativeQuery(sql);

    Object result = query.getSingleResult();
    assertNotNull(result, "Database should have at least one successful migration");
  }

  @Test
  public void testRequiredTablesExist() {
    // List of essential tables that should exist in the application
    String[] requiredTables = {
        "users",
        "questions",
        "quizzes",
        "players",
        "answer_options",
        "player_answers"
    };

    for (String tableName : requiredTables) {
      String sql = "SELECT EXISTS (" +
          "SELECT FROM information_schema.tables " +
          "WHERE table_schema = 'public' " +
          "AND table_name = '" + tableName + "')";

      Query query = entityManager.createNativeQuery(sql);
      boolean tableExists = false;

      try {
        tableExists = (boolean) query.getSingleResult();
      } catch (Exception e) {
        // If query fails, table doesn't exist
        tableExists = false;
      }

      assertTrue(tableExists, "Required table '" + tableName + "' should exist in the database");
    }
  }
}
