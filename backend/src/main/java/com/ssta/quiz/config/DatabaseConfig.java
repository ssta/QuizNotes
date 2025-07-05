/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.config;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * Database configuration for handling JSONB in PostgreSQL.
 */
@Configuration
public class DatabaseConfig {
// TODO - consolidate DatabaseConfig and HibernateConfig - we don't need two configs floating around!!
  /**
   * Custom PostgreSQL dialect that provides custom type mappings.
   */
  public static class CustomPostgreSQLDialect extends PostgreSQLDialect implements TypeContributor {

    public CustomPostgreSQLDialect() {
      super();
    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
      typeContributions.contributeJdbcType(new JsonbColumnType());
    }
  }
}
