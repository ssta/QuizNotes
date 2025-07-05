/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.testconfig;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * Base class for repository tests that need a test database.
 * Combines DataJpaTest capabilities with our standardized test container setup.
 *
 * This class uses the PostgreSQL test container instead of H2 for more
 * production-like repository testing. It disables Flyway and enables Hibernate's
 * DDL auto-creation for repository tests.
 */
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TestDatabaseConfig.Initializer.class)
@Import({TestDatabaseConfig.class, TestPropertiesConfig.class})
public abstract class AbstractRepositoryTest {
  // Common repository test setup and utilities can go here
}