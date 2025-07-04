/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.controller;

import com.jayway.jsonpath.JsonPath;
import com.ssta.quiz.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HealthController.class)
@Import(SecurityConfig.class)
class HealthControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void getHealthCheck() throws Exception {
    // Get current time for timestamp validation
    long beforeRequestTime = System.currentTimeMillis();

    mockMvc.perform(get("/api/public/health"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("UP"))
        .andExpect(jsonPath("$.service").value("quiz-poc"))
        .andExpect(jsonPath("$.timestamp").exists())
        // Additional assertion to verify timestamp is recent
        .andExpect(result -> {
          long timestamp = JsonPath.read(result.getResponse().getContentAsString(), "$.timestamp");
          long afterRequestTime = System.currentTimeMillis() + 1000; // Allow 1 second buffer
          assertTrue(timestamp >= beforeRequestTime && timestamp <= afterRequestTime,
              "Timestamp should be between request start and end times");
        });
  }

  @Test
  void healthEndpointShouldBeAccessibleWithoutAuthentication() throws Exception {
    // Health endpoint should be accessible without authentication
    // as it's often used by load balancers and monitoring systems
    mockMvc.perform(get("/api/public/health"))
        .andExpect(status().isOk());
  }
}
