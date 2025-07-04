package com.ssta.quiz.config;

import com.ssta.quiz.auth.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {SecurityConfig.class, SecurityConfigTest.TestConfig.class})
@Import(AuthController.class)
class SecurityConfigTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void publicEndpoints_shouldBeAccessibleWithoutAuthentication() throws Exception {
    // Test public endpoints
    mockMvc.perform(get("/api/public/test"))
        .andExpect(status().isNotFound()); // 404 because endpoint doesn't exist, but not 401

    mockMvc.perform(get("/api/auth/status"))
        .andExpect(status().isOk()); // This endpoint should exist and return 200

    mockMvc.perform(get("/api/player/profile"))
        .andExpect(status().isNotFound()); // 404 because endpoint doesn't exist, but not 401
  }

  @Test
  void adminEndpoints_shouldRequireAuthentication() throws Exception {
    // Test admin endpoints without authentication
    mockMvc.perform(get("/api/admin/dashboard"))
        .andExpect(status().isUnauthorized()); // 401 Unauthorized

    mockMvc.perform(get("/api/quizmaster/games"))
        .andExpect(status().isUnauthorized()); // 401 Unauthorized
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void adminEndpoints_shouldBeAccessibleWithAuthentication() throws Exception {
    // Test admin endpoints with authentication
    mockMvc.perform(get("/api/admin/dashboard"))
        .andExpect(status().isNotFound()); // 404 because endpoint doesn't exist, but not 401

    mockMvc.perform(get("/api/quizmaster/games"))
        .andExpect(status().isNotFound()); // 404 because endpoint doesn't exist, but not 401
  }

  // Inner configuration class to provide necessary mock beans
  @Configuration
  static class TestConfig {
    // Add any mock beans that might be needed for security configuration
  }
}