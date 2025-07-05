/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Additional Hibernate configuration to ensure proper type mappings.
 */
@Configuration
public class HibernateConfig {

  @Bean
  public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
    return hibernateProperties -> {
      // Add any additional Hibernate properties if needed
      hibernateProperties.put("hibernate.jdbc.batch_size", "50");
      hibernateProperties.put("hibernate.order_inserts", "true");
      hibernateProperties.put("hibernate.order_updates", "true");
      // Use the standard PostgreSQL dialect
      hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    };
  }
}
