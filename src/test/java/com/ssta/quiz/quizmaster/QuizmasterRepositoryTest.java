/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.quizmaster;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the QuizmasterRepository.
 */
@DataJpaTest
@ActiveProfiles("test")
public class QuizmasterRepositoryTest {

  @Autowired
  private QuizmasterRepository quizmasterRepository;

  @Test
  public void testSaveAndFindById() {
    // Given
    Quizmaster quizmaster = createTestQuizmaster("testuser", "twitch12345");

    // When
    Quizmaster savedQuizmaster = quizmasterRepository.save(quizmaster);
    Optional<Quizmaster> foundQuizmaster = quizmasterRepository.findById(savedQuizmaster.getId());

    // Then
    assertTrue(foundQuizmaster.isPresent());
    assertEquals(savedQuizmaster.getId(), foundQuizmaster.get().getId());
    assertEquals("testuser", foundQuizmaster.get().getUsername());
    assertEquals("twitch12345", foundQuizmaster.get().getTwitchId());
    assertEquals("test@example.com", foundQuizmaster.get().getEmail());
    assertNotNull(foundQuizmaster.get().getCreatedAt());
  }

  @Test
  public void testFindByUsername() {
    // Given
    Quizmaster quizmaster = createTestQuizmaster("findbyusername", "twitch54321");
    quizmasterRepository.save(quizmaster);

    // When
    Optional<Quizmaster> foundQuizmaster = quizmasterRepository.findByUsername("findbyusername");

    // Then
    assertTrue(foundQuizmaster.isPresent());
    assertEquals("findbyusername", foundQuizmaster.get().getUsername());
  }

  @Test
  public void testFindByTwitchId() {
    // Given
    Quizmaster quizmaster = createTestQuizmaster("twitchuser", "findbytwitch");
    quizmasterRepository.save(quizmaster);

    // When
    Optional<Quizmaster> foundQuizmaster = quizmasterRepository.findByTwitchId("findbytwitch");

    // Then
    assertTrue(foundQuizmaster.isPresent());
    assertEquals("findbytwitch", foundQuizmaster.get().getTwitchId());
  }

  @Test
  public void testExistsByUsername() {
    // Given
    Quizmaster quizmaster = createTestQuizmaster("existsuser", "twitch99999");
    quizmasterRepository.save(quizmaster);

    // When & Then
    assertTrue(quizmasterRepository.existsByUsername("existsuser"));
    assertFalse(quizmasterRepository.existsByUsername("nonexistentuser"));
  }

  @Test
  public void testExistsByTwitchId() {
    // Given
    Quizmaster quizmaster = createTestQuizmaster("twitchexistsuser", "twitchexists");
    quizmasterRepository.save(quizmaster);

    // When & Then
    assertTrue(quizmasterRepository.existsByTwitchId("twitchexists"));
    assertFalse(quizmasterRepository.existsByTwitchId("nonexistenttwitch"));
  }

  private Quizmaster createTestQuizmaster(String username, String twitchId) {
    return Quizmaster.builder()
        .username(username)
        .twitchId(twitchId)
        .email("test@example.com")
        .createdAt(LocalDateTime.now())
        .build();
  }
}
