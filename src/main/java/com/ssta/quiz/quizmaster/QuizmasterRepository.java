/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.quizmaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing Quizmaster entities.
 */
@Repository
public interface QuizmasterRepository extends JpaRepository<Quizmaster, Long> {

  /**
   * Find a quizmaster by username.
   *
   * @param username the username to search for
   * @return an Optional containing the quizmaster if found
   */
  Optional<Quizmaster> findByUsername(String username);

  /**
   * Find a quizmaster by Twitch ID.
   *
   * @param twitchId the Twitch ID to search for
   * @return an Optional containing the quizmaster if found
   */
  Optional<Quizmaster> findByTwitchId(String twitchId);

  /**
   * Check if a username is already taken.
   *
   * @param username the username to check
   * @return true if the username exists
   */
  boolean existsByUsername(String username);

  /**
   * Check if a Twitch ID is already registered.
   *
   * @param twitchId the Twitch ID to check
   * @return true if the Twitch ID exists
   */
  boolean existsByTwitchId(String twitchId);
}
