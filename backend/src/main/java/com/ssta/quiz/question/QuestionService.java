/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing question operations.
 */
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final ObjectMapper objectMapper;

  @Autowired
  public QuestionService(QuestionRepository questionRepository, ObjectMapper objectMapper) {
    this.questionRepository = questionRepository;
    this.objectMapper = objectMapper;
  }

  /**
   * Update the options for a question.
   *
   * @param questionId The ID of the question to update
   * @param options The list of options to set
   * @return true if update was successful, false otherwise
   * @throws JsonProcessingException if options cannot be serialized to JSON
   */
  @Transactional
  public boolean updateQuestionOptions(long questionId, List<QuestionOption> options) throws JsonProcessingException {
    String optionsJson = objectMapper.writeValueAsString(options);
    int rowsAffected = questionRepository.updateOptions(questionId, optionsJson);
    return rowsAffected > 0;
  }

  /**
   * Get the options for a question.
   *
   * @param question The question entity
   * @return List of question options, or null if no options are set
   * @throws JsonProcessingException if options cannot be deserialized from JSON
   */
  public List<QuestionOption> getQuestionOptions(Question question) throws JsonProcessingException {
    if (question.getOptions() == null || question.getOptions().isEmpty()) {
      return null;
    }
    return objectMapper.readValue(question.getOptions(), new TypeReference<List<QuestionOption>>() {
    });
  }
}
