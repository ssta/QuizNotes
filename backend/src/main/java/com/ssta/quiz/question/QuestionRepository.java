/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Question entity operations.
 * Provides methods to query and manipulate question data.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  /**
   * Find all questions for a specific quiz.
   *
   * @param quizId The ID of the quiz
   * @return List of questions belonging to the quiz
   */
  List<Question> findByQuizId(Long quizId);

  /**
   * Find all questions for a specific quiz with pagination support.
   *
   * @param quizId The ID of the quiz
   * @param pageable Pagination information
   * @return Page of questions belonging to the quiz
   */
  Page<Question> findByQuizId(Long quizId, Pageable pageable);

  /**
   * Find a specific question by its ID and quiz ID.
   * This provides an additional security check to ensure the question belongs to the specified quiz.
   *
   * @param id The question ID
   * @param quizId The quiz ID
   * @return Optional containing the question if found
   */
  Optional<Question> findByIdAndQuizId(Long id, Long quizId);

  /**
   * Count the number of questions in a quiz.
   *
   * @param quizId The ID of the quiz
   * @return The count of questions in the quiz
   */
  long countByQuizId(Long quizId);

  /**
   * Check if a question exists in a particular quiz.
   *
   * @param id The question ID
   * @param quizId The quiz ID
   * @return true if the question exists in the specified quiz
   */
  boolean existsByIdAndQuizId(Long id, Long quizId);

  /**
   * Delete all questions associated with a quiz.
   * This is useful when deleting a quiz and all its associated questions.
   *
   * @param quizId The ID of the quiz
   * @return The number of questions deleted
   */
  long deleteByQuizId(Long quizId);

  /**
   * Search for questions by text content with pagination.
   *
   * @param searchTerm The search term to look for in question text
   * @param pageable Pagination information
   * @return Page of matching questions
   */
  @Query("SELECT q FROM Question q WHERE LOWER(q.questionText) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
  Page<Question> searchByQuestionText(@Param("searchTerm") String searchTerm, Pageable pageable);

  /**
   * Update the order of a question within a quiz.
   *
   * @param id The question ID
   * @param orderIndex The new order index
   * @return The number of rows affected (should be 1)
   */
  @Modifying
  @Transactional
  @Query("UPDATE Question q SET q.orderIndex = :orderIndex WHERE q.id = :id")
  int updateOrderIndex(@Param("id") Long id, @Param("orderIndex") Integer orderIndex);

  /**
   * Update the options for a question.
   *
   * @param id The question ID
   * @param options The new options in JSON format
   * @return The number of rows affected (should be 1)
   */
  @Modifying
  @Transactional
  @Query("UPDATE Question q SET q.options = :options WHERE q.id = :id")
  int updateOptions(@Param("id") Long id, @Param("options") String options);

  /**
   * Find questions by their IDs and quiz ID.
   * Useful for batch operations on multiple questions within a quiz.
   *
   * @param ids Collection of question IDs
   * @param quizId The quiz ID
   * @return List of matching questions
   */
  @Query("SELECT q FROM Question q WHERE q.id IN :ids AND q.quizId = :quizId")
  List<Question> findByIdsAndQuizId(@Param("ids") List<Long> ids, @Param("quizId") Long quizId);
}
