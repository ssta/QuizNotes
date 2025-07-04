/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {WebConfig.class, WebConfigTest.TestConfig.class})
public class WebConfigTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void corsConfig_shouldAllowConfiguredOrigin() throws Exception {
    mockMvc.perform(options("/api/test")
            .header("Origin", "http://localhost:5173")
            .header("Access-Control-Request-Method", "GET")
            .header("Access-Control-Request-Headers", "Content-Type"))
        .andExpect(status().isOk())
        .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"))
        .andExpect(header().exists("Access-Control-Allow-Methods"))
        .andExpect(header().exists("Access-Control-Allow-Headers"));
  }

  @Test
  void corsConfig_shouldRejectUnauthorizedOrigin() throws Exception {
    mockMvc.perform(options("/api/test")
            .header("Origin", "http://unauthorized-origin.com")
            .header("Access-Control-Request-Method", "GET")
            .header("Access-Control-Request-Headers", "Content-Type"))
        .andExpect(status().isForbidden());
  }

  // Inner configuration class to provide mock beans
  @Configuration
  static class TestConfig {
    // This class will be used to provide mock beans if needed
  }
}
