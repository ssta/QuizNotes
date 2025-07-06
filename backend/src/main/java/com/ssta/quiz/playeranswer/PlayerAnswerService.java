/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.playeranswer;

import com.ssta.quiz.player.Player;
import com.ssta.quiz.player.PlayerRepository;
import com.ssta.quiz.question.Question;
import com.ssta.quiz.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing player answers to quiz questions.
 */
@Service
public class PlayerAnswerService {

  private final PlayerAnswerRepository playerAnswerRepository;
  private final PlayerRepository playerRepository;
  private final QuestionRepository questionRepository;

  @Autowired
  public PlayerAnswerService(PlayerAnswerRepository playerAnswerRepository,
                             PlayerRepository playerRepository,
                             QuestionRepository questionRepository) {
    this.playerAnswerRepository = playerAnswerRepository;
    this.playerRepository = playerRepository;
    this.questionRepository = questionRepository;
  }

  /**
   * Record a player's answer to a question.
   *
   * @param playerId the player ID
   * @param questionId the question ID
   * @param answerOption the selected answer option
   * @param responseTimeMs time taken to answer in milliseconds
   * @return the created PlayerAnswer entity
   * @throws IllegalArgumentException if player or question not found
   */
  @Transactional
  public PlayerAnswer recordAnswer(Long playerId, Long questionId, Integer answerOption, Integer responseTimeMs) {
    Player player = playerRepository.findById(playerId)
        .orElseThrow(() -> new IllegalArgumentException("Player not found: " + playerId));

    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionId));

    // Check if answer already exists
    Optional<PlayerAnswer> existingAnswer = playerAnswerRepository.findByPlayerIdAndQuestionId(playerId, questionId);
    if (existingAnswer.isPresent()) {
      throw new IllegalStateException("Player has already answered this question");
    }

    // Calculate if answer is correct and score based on game rules
    // This is placeholder logic - actual implementation would depend on how questions store correct answers
    boolean correct = isCorrectAnswer(question, answerOption);
    Integer score = calculateScore(correct, responseTimeMs, question.getTimeLimit());

    PlayerAnswer playerAnswer = PlayerAnswer.builder()
        .player(player)
        .question(question)
        .answerOption(answerOption)
        .correct(correct)
        .responseTimeMs(responseTimeMs)
        .score(score)
        .build();

    return playerAnswerRepository.save(playerAnswer);
  }

  /**
   * Get all answers for a specific player.
   *
   * @param playerId the player ID
   * @return list of player answers
   */
  public List<PlayerAnswer> getPlayerAnswers(Long playerId) {
    return playerAnswerRepository.findByPlayerId(playerId);
  }

  /**
   * Get all answers for a specific question.
   *
   * @param questionId the question ID
   * @return list of answers for the question
   */
  public List<PlayerAnswer> getQuestionAnswers(Long questionId) {
    return playerAnswerRepository.findByQuestionId(questionId);
  }

  /**
   * Calculate total score for a player in a quiz.
   *
   * @param playerId the player ID
   * @return total score
   */
  public int calculateTotalScore(Long playerId) {
    return playerAnswerRepository.findByPlayerId(playerId).stream()
        .mapToInt(answer -> answer.getScore() != null ? answer.getScore() : 0)
        .sum();
  }

  /**
   * Determines if the selected answer option is correct.
   * This is a placeholder implementation - in a real application, this would check against
   * the question's correct answer data.
   */
  private boolean isCorrectAnswer(Question question, Integer answerOption) {
    // Placeholder logic - would need to be replaced with actual implementation
    // that checks against the question's stored correct answer
    return true; // For testing purposes
  }

  /**
   * Calculate score based on correctness and response time.
   * Faster correct answers get higher scores.
   */
  private Integer calculateScore(boolean correct, Integer responseTimeMs, Integer timeLimit) {
      if (!correct) {return 0;}

    // Simple scoring formula: maximum score for instant answers, decreasing with time
    int maxScore = 100;
    int minScore = 10;

    if (responseTimeMs == null || timeLimit == null || timeLimit <= 0) {
      return correct ? maxScore : 0;
    }

    // Convert timeLimit from seconds to milliseconds
    int timeLimitMs = timeLimit * 1000;

    // Calculate score based on response time
    if (responseTimeMs >= timeLimitMs) {
      return minScore; // Minimum score for correct answers at time limit
    }

    // Linear score reduction based on time used
    return (int) (maxScore - ((maxScore - minScore) * responseTimeMs / (double) timeLimitMs));
  }
}
