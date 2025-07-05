/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing Player entities.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  /**
   * Find a player by their username.
   *
   * @param username the username to search for
   * @return an Optional containing the player if found, or empty if not found
   */
  Optional<Player> findByUsername(String username);

  /**
   * Find a player by their Twitch ID.
   *
   * @param twitchId the Twitch ID to search for
   * @return an Optional containing the player if found, or empty if not found
   */
  Optional<Player> findByTwitchId(String twitchId);

  /**
   * Check if a player with the given username exists.
   *
   * @param username the username to check
   * @return true if a player with the username exists, false otherwise
   */
  boolean existsByUsername(String username);

  /**
   * Check if a player with the given Twitch ID exists.
   *
   * @param twitchId the Twitch ID to check
   * @return true if a player with the Twitch ID exists, false otherwise
   */
  boolean existsByTwitchId(String twitchId);
}
