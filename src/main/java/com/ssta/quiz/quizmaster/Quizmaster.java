/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.quizmaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a quiz master (creator/host) in the system.
 */
@Entity
@Table(name = "quizmaster")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quizmaster {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(name = "twitch_id", nullable = false, unique = true, length = 50)
  private String twitchId;

  @Column(length = 255)
  private String email;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }
}
