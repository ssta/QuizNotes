package com.ssta.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable) // Temporarily disabled for initial development
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/public/**").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/player/**").permitAll()
            .requestMatchers("/api/admin/**").authenticated()
            .requestMatchers("/api/quizmaster/**").authenticated()
        )
        // Add OAuth configuration here later
        .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic Auth to get 401 for unauthenticated

    return http.build();
  }
}