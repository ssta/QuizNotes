/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.player;

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
 * Entity representing a player in the quiz system.
 */
@Entity
@Table(name = "players")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(name = "twitch_id", nullable = false, unique = true, length = 50)
  private String twitchId;

  @Column(name = "display_name", length = 100)
  private String displayName;

  @Column(length = 255)
  private String email;

  @Column(length = 255)
  private String avatar;

  @Column(name = "total_points")
  private Long totalPoints;

  @Column(name = "games_played")
  private Integer gamesPlayed;

  @Column(name = "games_won")
  private Integer gamesWon;

  @Column(name = "created_at", nullable = false, updatable = false)
  private ZonedDateTime createdAt;

  @Column(name = "last_login")
  private ZonedDateTime lastLogin;

  @PrePersist
  protected void onCreate() {
    createdAt = ZonedDateTime.now();
    if (totalPoints == null) {
      totalPoints = 0L;
    }
    if (gamesPlayed == null) {
      gamesPlayed = 0;
    }
    if (gamesWon == null) {
      gamesWon = 0;
    }
  }
}
