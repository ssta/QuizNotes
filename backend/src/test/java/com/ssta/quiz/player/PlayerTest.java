/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.player;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Player entity class.
 */
public class PlayerTest {

  @Test
  public void testPlayerBuilder() {
    // Given
    Long id = 1L;
    String username = "player1";
    String twitchId = "twitch12345";
    String displayName = "Player One";
    String email = "player@example.com";
    String avatar = "https://example.com/avatar.png";
    Long totalPoints = 1000L;
    Integer gamesPlayed = 10;
    Integer gamesWon = 3;
    ZonedDateTime createdAt = ZonedDateTime.now();
    ZonedDateTime lastLogin = ZonedDateTime.now().plusHours(1);

    // When
    Player player = Player.builder()
        .id(id)
        .username(username)
        .twitchId(twitchId)
        .displayName(displayName)
        .email(email)
        .avatar(avatar)
        .totalPoints(totalPoints)
        .gamesPlayed(gamesPlayed)
        .gamesWon(gamesWon)
        .createdAt(createdAt)
        .lastLogin(lastLogin)
        .build();

    // Then
    assertEquals(id, player.getId());
    assertEquals(username, player.getUsername());
    assertEquals(twitchId, player.getTwitchId());
    assertEquals(displayName, player.getDisplayName());
    assertEquals(email, player.getEmail());
    assertEquals(avatar, player.getAvatar());
    assertEquals(totalPoints, player.getTotalPoints());
    assertEquals(gamesPlayed, player.getGamesPlayed());
    assertEquals(gamesWon, player.getGamesWon());
    assertEquals(createdAt, player.getCreatedAt());
    assertEquals(lastLogin, player.getLastLogin());
  }

  @Test
  public void testPlayerNoArgsConstructor() {
    // When
    Player player = new Player();

    // Then
    assertNull(player.getId());
    assertNull(player.getUsername());
    assertNull(player.getTwitchId());
    assertNull(player.getDisplayName());
    assertNull(player.getEmail());
    assertNull(player.getAvatar());
    assertNull(player.getTotalPoints());
    assertNull(player.getGamesPlayed());
    assertNull(player.getGamesWon());
    assertNull(player.getCreatedAt());
    assertNull(player.getLastLogin());
  }

  @Test
  public void testPlayerAllArgsConstructor() {
    // Given
    Long id = 1L;
    String username = "player1";
    String twitchId = "twitch12345";
    String displayName = "Player One";
    String email = "player@example.com";
    String avatar = "https://example.com/avatar.png";
    Long totalPoints = 1000L;
    Integer gamesPlayed = 10;
    Integer gamesWon = 3;
    ZonedDateTime createdAt = ZonedDateTime.now();
    ZonedDateTime lastLogin = ZonedDateTime.now().plusHours(1);

    // When
    Player player = new Player(id, username, twitchId, displayName, email, avatar,
        totalPoints, gamesPlayed, gamesWon, createdAt, lastLogin);

    // Then
    assertEquals(id, player.getId());
    assertEquals(username, player.getUsername());
    assertEquals(twitchId, player.getTwitchId());
    assertEquals(displayName, player.getDisplayName());
    assertEquals(email, player.getEmail());
    assertEquals(avatar, player.getAvatar());
    assertEquals(totalPoints, player.getTotalPoints());
    assertEquals(gamesPlayed, player.getGamesPlayed());
    assertEquals(gamesWon, player.getGamesWon());
    assertEquals(createdAt, player.getCreatedAt());
    assertEquals(lastLogin, player.getLastLogin());
  }

  @Test
  public void testPrePersistSetsCreatedAtAndDefaults() {
    // Given
    Player player = new Player();
    assertNull(player.getCreatedAt());
    assertNull(player.getTotalPoints());
    assertNull(player.getGamesPlayed());
    assertNull(player.getGamesWon());

    // When
    player.onCreate();

    // Then
    assertNotNull(player.getCreatedAt());
    assertEquals(0L, player.getTotalPoints());
    assertEquals(0, player.getGamesPlayed());
    assertEquals(0, player.getGamesWon());
    assertTrue(player.getCreatedAt().isBefore(ZonedDateTime.now().plusSeconds(1)));
    assertTrue(player.getCreatedAt().isAfter(ZonedDateTime.now().minusSeconds(1)));
  }
}
