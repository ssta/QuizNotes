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
   * @return an Optional containing the player if found, or empty if not found
   */
  Optional<Player> findByNickname(String nickname);


  /**
   * Check if a player with the given username exists.
   *
   * @return true if a player with the username exists, false otherwise
   */
  boolean existsByNickname(String nickname);
}
