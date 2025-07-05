/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.quizmaster;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Quizmaster entity class.
 */
public class QuizmasterTest {

  @Test
  public void testQuizmasterBuilder() {
    // Given
    Long id = 1L;
    String username = "testuser";
    String twitchId = "twitch12345";
    String email = "test@example.com";
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime lastLogin = LocalDateTime.now().plusHours(1);

    // When
    Quizmaster quizmaster = Quizmaster.builder()
        .id(id)
        .username(username)
        .twitchId(twitchId)
        .email(email)
        .createdAt(createdAt)
        .lastLogin(lastLogin)
        .build();

    // Then
    assertEquals(id, quizmaster.getId());
    assertEquals(username, quizmaster.getUsername());
    assertEquals(twitchId, quizmaster.getTwitchId());
    assertEquals(email, quizmaster.getEmail());
    assertEquals(createdAt, quizmaster.getCreatedAt());
    assertEquals(lastLogin, quizmaster.getLastLogin());
  }

  @Test
  public void testQuizmasterNoArgsConstructor() {
    // When
    Quizmaster quizmaster = new Quizmaster();

    // Then
    assertNull(quizmaster.getId());
    assertNull(quizmaster.getUsername());
    assertNull(quizmaster.getTwitchId());
    assertNull(quizmaster.getEmail());
    assertNull(quizmaster.getCreatedAt());
    assertNull(quizmaster.getLastLogin());
  }

  @Test
  public void testQuizmasterAllArgsConstructor() {
    // Given
    Long id = 1L;
    String username = "testuser";
    String twitchId = "twitch12345";
    String email = "test@example.com";
    ZonedDateTime createdAt = ZonedDateTime.now();
    ZonedDateTime lastLogin = ZonedDateTime.now().plusHours(1);

    // When
    Quizmaster quizmaster = new Quizmaster(id, username, twitchId, email, createdAt, lastLogin);

    // Then
    assertEquals(id, quizmaster.getId());
    assertEquals(username, quizmaster.getUsername());
    assertEquals(twitchId, quizmaster.getTwitchId());
    assertEquals(email, quizmaster.getEmail());
    assertEquals(createdAt, quizmaster.getCreatedAt());
    assertEquals(lastLogin, quizmaster.getLastLogin());
  }

  @Test
  public void testQuizmasterEqualsAndHashCode() {
    // Given
    Quizmaster quizmaster1 = new Quizmaster(1L, "user1", "twitch1", "email1@example.com",
        ZonedDateTime.now(), ZonedDateTime.now());
    Quizmaster quizmaster2 = new Quizmaster(1L, "user1", "twitch1", "email1@example.com",
        quizmaster1.getCreatedAt(), quizmaster1.getLastLogin());
    Quizmaster quizmaster3 = new Quizmaster(2L, "user2", "twitch2", "email2@example.com",
        ZonedDateTime.now(), ZonedDateTime.now());

    // Then
    assertEquals(quizmaster1, quizmaster2);
    assertEquals(quizmaster1.hashCode(), quizmaster2.hashCode());
    assertNotEquals(quizmaster1, quizmaster3);
    assertNotEquals(quizmaster1.hashCode(), quizmaster3.hashCode());
  }

  @Test
  public void testPrePersistSetsCreatedAt() {
    // Given
    Quizmaster quizmaster = new Quizmaster();
    assertNull(quizmaster.getCreatedAt());

    // When
    quizmaster.onCreate();

    // Then
    assertNotNull(quizmaster.getCreatedAt());
    assertTrue(quizmaster.getCreatedAt().isBefore(ZonedDateTime.now().plusSeconds(1)));
    assertTrue(quizmaster.getCreatedAt().isAfter(ZonedDateTime.now().minusSeconds(1)));
  }
}
