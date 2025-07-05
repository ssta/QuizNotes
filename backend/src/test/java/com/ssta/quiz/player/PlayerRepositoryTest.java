/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.player;

import com.ssta.quiz.testconfig.AbstractRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;
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
    assertEquals("testplayer", foundPlayer.get().getUsername());
    assertEquals("twitch12345", foundPlayer.get().getTwitchId());
    assertEquals("Test Player", foundPlayer.get().getDisplayName());
    assertEquals("player@example.com", foundPlayer.get().getEmail());
    assertNotNull(foundPlayer.get().getCreatedAt());
  }

  @Test
  public void testFindByUsername() {
    // Given
    Player player = createTestPlayer("findbyusername", "twitch54321");
    playerRepository.save(player);

    // When
    Optional<Player> foundPlayer = playerRepository.findByUsername("findbyusername");

    // Then
    assertTrue(foundPlayer.isPresent());
    assertEquals("findbyusername", foundPlayer.get().getUsername());
  }

  @Test
  public void testFindByTwitchId() {
    // Given
    Player player = createTestPlayer("twitchuser", "findbytwitch");
    playerRepository.save(player);

    // When
    Optional<Player> foundPlayer = playerRepository.findByTwitchId("findbytwitch");

    // Then
    assertTrue(foundPlayer.isPresent());
    assertEquals("findbytwitch", foundPlayer.get().getTwitchId());
  }

  @Test
  public void testExistsByUsername() {
    // Given
    Player player = createTestPlayer("existsuser", "twitch99999");
    playerRepository.save(player);

    // When & Then
    assertTrue(playerRepository.existsByUsername("existsuser"));
    assertFalse(playerRepository.existsByUsername("nonexistentuser"));
  }

  @Test
  public void testExistsByTwitchId() {
    // Given
    Player player = createTestPlayer("twitchexistsuser", "twitchexists");
    playerRepository.save(player);

    // When & Then
    assertTrue(playerRepository.existsByTwitchId("twitchexists"));
    assertFalse(playerRepository.existsByTwitchId("nonexistenttwitch"));
  }

  @Test
  public void testFindAllOrderByTotalPointsDesc() {
    // Given
    Player player1 = createTestPlayer("highscore", "twitch1");
    player1.setTotalPoints(1000L);

    Player player2 = createTestPlayer("midscore", "twitch2");
    player2.setTotalPoints(500L);

    Player player3 = createTestPlayer("lowscore", "twitch3");
    player3.setTotalPoints(100L);

    playerRepository.saveAll(List.of(player3, player1, player2));

    // When
    List<Player> players = playerRepository.findAll();
    players.sort((a, b) -> b.getTotalPoints().compareTo(a.getTotalPoints()));

    // Then
    assertEquals(3, players.size());
    assertEquals("highscore", players.get(0).getUsername());
    assertEquals("midscore", players.get(1).getUsername());
    assertEquals("lowscore", players.get(2).getUsername());
  }

  private Player createTestPlayer(String username, String twitchId) {
    return Player.builder()
        .username(username)
        .twitchId(twitchId)
        .displayName("Test Player")
        .email("player@example.com")
        .avatar("https://example.com/avatar.png")
        .totalPoints(0L)
        .gamesPlayed(0)
        .gamesWon(0)
        .createdAt(ZonedDateTime.now())
        .build();
  }
}
