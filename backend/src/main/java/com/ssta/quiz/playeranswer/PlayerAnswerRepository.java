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

  Optional<PlayerAnswer> findByPlayerIdAndQuestionId(Long playerId, Long questionId);

  List<PlayerAnswer> findByPlayerId(Long playerId);

  List<PlayerAnswer> findByQuestionId(Long questionId);

  List<PlayerAnswer> findByQuestionIdAndAnswerOption(Long questionId, int answerOption);
}
