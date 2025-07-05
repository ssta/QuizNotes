/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.question;

import com.ssta.quiz.quiz.Quiz;
import com.ssta.quiz.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for QuestionRepository using DataJpaTest for testing JPA repositories.
 * These tests verify that query methods work as expected with the actual database schema.
 */
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=PostgreSQL",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.properties.hibernate.type.preferred_uuid_jdbc_type=CHAR"
})
public class QuestionRepositoryTest {

  @Autowired
  private TestEntityManager entityManager; // Special EntityManager for testing

  @Autowired
  private QuestionRepository questionRepository;

  private User testUser;
  private Quiz testQuiz1;
  private Quiz testQuiz2;
  private Question question1;
  private Question question2;
  private Question question3;
  private Question question4;

  @BeforeEach
  public void setup() {
    // Create test user
    testUser = new User();
    // ID will be auto-generated
    testUser.setUsername("testuser");
    testUser.setEmail("test@example.com");
    testUser.setCreatedAt(ZonedDateTime.now());
    testUser.setUpdatedAt(ZonedDateTime.now());
    entityManager.persist(testUser);

    // Create test quizzes
    testQuiz1 = new Quiz();
    // ID will be auto-generated
    testQuiz1.setTitle("Quiz 1");
    testQuiz1.setDescription("Description for Quiz 1");
    testQuiz1.setUserId(testUser.getId());
    testQuiz1.setStatus("DRAFT");
    testQuiz1.setCreatedAt(ZonedDateTime.now());
    testQuiz1.setUpdatedAt(ZonedDateTime.now());
    entityManager.persist(testQuiz1);

    testQuiz2 = new Quiz();
    // ID will be auto-generated
    testQuiz2.setTitle("Quiz 2");
    testQuiz2.setDescription("Description for Quiz 2");
    testQuiz2.setUserId(testUser.getId());
    testQuiz2.setStatus("DRAFT");
    testQuiz2.setCreatedAt(ZonedDateTime.now());
    testQuiz2.setUpdatedAt(ZonedDateTime.now());
    entityManager.persist(testQuiz2);

    // Create test questions for quiz 1
    question1 = new Question();
    // ID will be auto-generated
    question1.setQuizId(testQuiz1.getId());
    question1.setQuestionText("What is Spring Boot?");
    question1.setTimeLimit(30);
    question1.setOrderIndex(1);
    question1.setCreatedAt(ZonedDateTime.now());
    question1.setUpdatedAt(ZonedDateTime.now());
    entityManager.persist(question1);

    question2 = new Question();
    // ID will be auto-generated
    question2.setQuizId(testQuiz1.getId());
    question2.setQuestionText("What is Spring Data JPA?");
    question2.setTimeLimit(30);
    question2.setOrderIndex(2);
    question2.setCreatedAt(ZonedDateTime.now());
    question2.setUpdatedAt(ZonedDateTime.now());
    entityManager.persist(question2);

    // Create test questions for quiz 2
    question3 = new Question();
    // ID will be auto-generated
    question3.setQuizId(testQuiz2.getId());
    question3.setQuestionText("What is Java?");
    question3.setTimeLimit(20);
    question3.setOrderIndex(1);
    question3.setCreatedAt(ZonedDateTime.now());
    question3.setUpdatedAt(ZonedDateTime.now());
    entityManager.persist(question3);

    question4 = new Question();
    // ID will be auto-generated
    question4.setQuizId(testQuiz2.getId());
    question4.setQuestionText("What is a Spring repository?");
    question4.setTimeLimit(25);
    question4.setOrderIndex(2);
    question4.setImageUrl("http://example.com/image.jpg");
    question4.setCreatedAt(ZonedDateTime.now());
    question4.setUpdatedAt(ZonedDateTime.now());
    entityManager.persist(question4);

    // Flush to ensure all entities are in the database
    entityManager.flush();
  }

  @Test
  @DisplayName("Should find questions by quiz ID")
  public void testFindByQuizId() {
    // When
    List<Question> foundQuestions = questionRepository.findByQuizId(testQuiz1.getId());

    // Then
    assertThat(foundQuestions).hasSize(2);
    assertThat(foundQuestions).extracting(Question::getId)
        .containsExactlyInAnyOrder(question1.getId(), question2.getId());
  }

  @Test
  @DisplayName("Should find questions by quiz ID with pagination")
  public void testFindByQuizIdWithPagination() {
    // When
    PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("orderIndex").ascending());
    Page<Question> page = questionRepository.findByQuizId(testQuiz1.getId(), pageRequest);

    // Then
    assertThat(page.getTotalElements()).isEqualTo(2);
    assertThat(page.getContent()).hasSize(1);
    assertThat(page.getContent().get(0).getId()).isEqualTo(question1.getId());

    // Check second page
    PageRequest secondPageRequest = PageRequest.of(1, 1, Sort.by("orderIndex").ascending());
    Page<Question> secondPage = questionRepository.findByQuizId(testQuiz1.getId(), secondPageRequest);
    assertThat(secondPage.getContent()).hasSize(1);
    assertThat(secondPage.getContent().get(0).getId()).isEqualTo(question2.getId());
  }

  @Test
  @DisplayName("Should find question by ID and quiz ID")
  public void testFindByIdAndQuizId() {
    // When
    Optional<Question> foundQuestion = questionRepository.findByIdAndQuizId(question1.getId(), testQuiz1.getId());

    // Then
    assertThat(foundQuestion).isPresent();
    assertThat(foundQuestion.get().getQuestionText()).isEqualTo(question1.getQuestionText());

    // When looking for a question with the wrong quiz ID, it should not be found
    Optional<Question> notFoundQuestion = questionRepository.findByIdAndQuizId(question1.getId(), testQuiz2.getId());
    assertThat(notFoundQuestion).isEmpty();
  }

  @Test
  @DisplayName("Should count questions by quiz ID")
  public void testCountByQuizId() {
    // When
    long count = questionRepository.countByQuizId(testQuiz1.getId());

    // Then
    assertThat(count).isEqualTo(2);

    // Count for a quiz with no questions
    Long emptyQuizId = 999L; // Non-existent ID
    assertThat(questionRepository.countByQuizId(emptyQuizId)).isZero();
  }

  @Test
  @DisplayName("Should check if a question exists by ID and quiz ID")
  public void testExistsByIdAndQuizId() {
    // When
    boolean exists = questionRepository.existsByIdAndQuizId(question1.getId(), testQuiz1.getId());

    // Then
    assertThat(exists).isTrue();

    // When checking for a question with the wrong quiz ID
    boolean doesNotExist = questionRepository.existsByIdAndQuizId(question1.getId(), testQuiz2.getId());
    assertThat(doesNotExist).isFalse();
  }

  @Test
  @DisplayName("Should delete questions by quiz ID")
  public void testDeleteByQuizId() {
    // When
    long deletedCount = questionRepository.deleteByQuizId(testQuiz1.getId());

    // Then
    assertThat(deletedCount).isEqualTo(2);
    assertThat(questionRepository.findByQuizId(testQuiz1.getId())).isEmpty();

    // Questions from other quizzes should remain
    assertThat(questionRepository.findByQuizId(testQuiz2.getId())).hasSize(2);
  }

  @Test
  @DisplayName("Should search questions by text content")
  public void testSearchByQuestionText() {
    // When
    Page<Question> foundQuestions = questionRepository.searchByQuestionText("spring", PageRequest.of(0, 10));

    // Then
    assertThat(foundQuestions.getTotalElements()).isEqualTo(3); // 3 questions contain "spring"
    assertThat(foundQuestions.getContent())
        .extracting(Question::getId)
        .containsExactlyInAnyOrder(question1.getId(), question2.getId(), question4.getId());

    // Search should be case-insensitive
    Page<Question> foundQuestionsLowerCase = questionRepository.searchByQuestionText("SPRING", PageRequest.of(0, 10));
    assertThat(foundQuestionsLowerCase.getTotalElements()).isEqualTo(3);
  }

  @Test
  @DisplayName("Should update order index of a question")
  @Transactional
  public void testUpdateOrderIndex() {
    // When
    int updatedRows = questionRepository.updateOrderIndex(question1.getId(), 5);
    entityManager.flush(); // Force flush to ensure changes are visible

    // Then
    assertThat(updatedRows).isEqualTo(1);

    // Clear the persistence context to ensure we get a fresh entity from the database
    entityManager.clear();

    // Refresh the entity from the database
    Question updatedQuestion = entityManager.find(Question.class, question1.getId());
    assertThat(updatedQuestion.getOrderIndex()).isEqualTo(5);
  }

  @Test
  @DisplayName("Should find questions by IDs and quiz ID")
  public void testFindByIdsAndQuizId() {
    // When
    List<Long> ids = Arrays.asList(question1.getId(), question2.getId());
    List<Question> foundQuestions = questionRepository.findByIdsAndQuizId(ids, testQuiz1.getId());

    // Then
    assertThat(foundQuestions).hasSize(2);
    assertThat(foundQuestions).extracting(Question::getId)
        .containsExactlyInAnyOrder(question1.getId(), question2.getId());

    // When including an ID from another quiz, it should not be returned
    List<Long> mixedIds = Arrays.asList(question1.getId(), question3.getId());
    List<Question> mixedQuestions = questionRepository.findByIdsAndQuizId(mixedIds, testQuiz1.getId());
    assertThat(mixedQuestions).hasSize(1);
    assertThat(mixedQuestions.get(0).getId()).isEqualTo(question1.getId());
  }

  @Test
  @DisplayName("Should update options for a question")
  @Transactional
  public void testUpdateOptions() throws Exception {
    // Given
    String optionsJson = "[{\"text\":\"Option A\",\"correct\":false},{\"text\":\"Option B\",\"correct\":true}]";

    // When
    int updatedRows = questionRepository.updateOptions(question1.getId(), optionsJson);
    entityManager.flush();

    // Then
    assertThat(updatedRows).isEqualTo(1);

    // Clear the persistence context to ensure we get a fresh entity
    entityManager.clear();

    // Refresh the entity from the database
    Question updatedQuestion = entityManager.find(Question.class, question1.getId());
    assertThat(updatedQuestion.getOptions()).isNotNull();
    assertThat(updatedQuestion.getOptions()).contains("Option A");
    assertThat(updatedQuestion.getOptions()).contains("Option B");
    assertThat(updatedQuestion.getOptions()).contains("correct\":true");
  }
}
