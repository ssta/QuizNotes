/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

  @Mock
  private QuestionRepository questionRepository;

  @Spy
  private ObjectMapper objectMapper = new ObjectMapper();

  @InjectMocks
  private QuestionService questionService;

  private long questionId;
  private List<QuestionOption> options;

  @BeforeEach
  public void setup() {
    questionId = 99L;
    options = Arrays.asList(
        new QuestionOption("Option A", false),
        new QuestionOption("Option B", true),
        new QuestionOption("Option C", false),
        new QuestionOption("Option D", false)
    );
  }

  @Test
  @DisplayName("Should update question options")
  public void testUpdateQuestionOptions() throws Exception {
    // Given
    when(questionRepository.updateOptions(eq(questionId), any(String.class))).thenReturn(1);

    // When
    boolean result = questionService.updateQuestionOptions(questionId, options);

    // Then
    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("Should get question options from JSON")
  public void testGetQuestionOptions() throws Exception {
    // Given
    Question question = new Question();
    String optionsJson = objectMapper.writeValueAsString(options);
    question.setOptions(optionsJson);

    // When
    List<QuestionOption> retrievedOptions = questionService.getQuestionOptions(question);

    // Then
    assertThat(retrievedOptions).hasSize(4);
    assertThat(retrievedOptions.get(0).getText()).isEqualTo("Option A");
    assertThat(retrievedOptions.get(0).isCorrect()).isFalse();
    assertThat(retrievedOptions.get(1).getText()).isEqualTo("Option B");
    assertThat(retrievedOptions.get(1).isCorrect()).isTrue();
  }

  @Test
  @DisplayName("Should return null for null options")
  public void testGetQuestionOptionsNullJson() throws Exception {
    // Given
    Question question = new Question();
    question.setOptions(null);

    // When
    List<QuestionOption> retrievedOptions = questionService.getQuestionOptions(question);

    // Then
    assertThat(retrievedOptions).isNull();
  }
}
