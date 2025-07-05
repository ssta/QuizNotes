/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.testconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

/**
 * Configuration class that disables Flyway for repository tests.
 * This is useful for repository tests where we want Hibernate to manage the schema.
 */
@Configuration
@TestPropertySource(properties = {
    "spring.flyway.enabled=false"
})
public class TestPropertiesConfig {
  // Configuration placeholder - properties are set via annotations
}
