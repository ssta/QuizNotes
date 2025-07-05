/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.testconfig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
@Testcontainers
@ActiveProfiles("test")
public class TestDatabaseConfig {

  // Static container shared across all test classes
  @Container
  private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.4"))
          .withDatabaseName("testdb")
          .withUsername("test")
          .withPassword("test");

  static {
    // Start the container when class is loaded
    POSTGRES_CONTAINER.start();
  }

  @Bean
  public PostgreSQLContainer<?> postgreSQLContainer() {
    return POSTGRES_CONTAINER;
  }

  // Define a static inner class for initializing context
  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url=" + POSTGRES_CONTAINER.getJdbcUrl(),
          "spring.datasource.username=" + POSTGRES_CONTAINER.getUsername(),
          "spring.datasource.password=" + POSTGRES_CONTAINER.getPassword(),
          "spring.datasource.driver-class-name=org.postgresql.Driver"
      ).applyTo(applicationContext.getEnvironment());
    }
  }
}
