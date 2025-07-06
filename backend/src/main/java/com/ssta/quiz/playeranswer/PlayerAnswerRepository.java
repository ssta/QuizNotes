/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.playeranswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing and manipulating PlayerAnswer entities.
 */
@Repository
public interface PlayerAnswerRepository extends JpaRepository<PlayerAnswer, Long> {

  /**
   * Find a player's answer for a specific question.
   *
   * @param playerId the ID of the player
   * @param questionId the ID of the question
   * @return the player's answer if found
   */
  Optional<PlayerAnswer> findByPlayerIdAndQuestionId(Long playerId, Long questionId);

  /**
   * Find all answers submitted by a specific player.
   *
   * @param playerId the ID of the player
   * @return list of answers submitted by the player
   */
  List<PlayerAnswer> findByPlayerId(Long playerId);

  /**
   * Find all answers for a specific question.
   *
   * @param questionId the ID of the question
   * @return list of all player answers for the question
   */
  List<PlayerAnswer> findByQuestionId(Long questionId);
}
