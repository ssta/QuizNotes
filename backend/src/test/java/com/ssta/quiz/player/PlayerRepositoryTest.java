/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.player;

import com.ssta.quiz.testconfig.AbstractRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the PlayerRepository.
 */
public class PlayerRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private PlayerRepository playerRepository;

  @Test
  public void testSaveAndFindById() {
    // Given
    Player player = createTestPlayer("testplayer", "twitch12345");

    // When
    Player savedPlayer = playerRepository.save(player);
    Optional<Player> foundPlayer = playerRepository.findById(savedPlayer.getId());

    // Then
    assertTrue(foundPlayer.isPresent());
    assertEquals(savedPlayer.getId(), foundPlayer.get().getId());
    assertEquals("testplayer", foundPlayer.get().getNickname());
  }

  @Test
  public void testFindByUsername() {
    // Given
    Player player = createTestPlayer("findbyusername", "twitch54321");
    playerRepository.save(player);

    // When
    Optional<Player> foundPlayer = playerRepository.findByNickname("findbyusername");

    // Then
    assertTrue(foundPlayer.isPresent());
    assertEquals("findbyusername", foundPlayer.get().getNickname());
  }

  @Test
  public void testExistsByNickname() {
    // Given
    Player player = createTestPlayer("existsuser", "twitch99999");
    playerRepository.save(player);

    // When & Then
    assertTrue(playerRepository.existsByNickname("existsuser"));
    assertFalse(playerRepository.existsByNickname("nonexistentuser"));
  }

  private Player createTestPlayer(String username, String twitchId) {
    return Player.builder()
        .nickname(username)
        .build();
  }
}
