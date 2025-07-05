/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Jackson JSON processing.
 */
@Configuration
public class JacksonConfig {

  /**
   * Creates and configures the ObjectMapper for JSON processing.
   *
   * @return The configured ObjectMapper instance
   */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    // Add any custom configurations for Jackson here if needed
    return objectMapper;
  }
}
