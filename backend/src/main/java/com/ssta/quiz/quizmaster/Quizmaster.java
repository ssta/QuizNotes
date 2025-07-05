/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.quizmaster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Entity representing a quiz master (creator/host) in the system.
 */
@Entity
@Table(name = "quizmasters")
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
  private ZonedDateTime createdAt;

  @Column(name = "last_login")
  private ZonedDateTime lastLogin;

  @PrePersist
  protected void onCreate() {
    createdAt = ZonedDateTime.now();
  }
}
