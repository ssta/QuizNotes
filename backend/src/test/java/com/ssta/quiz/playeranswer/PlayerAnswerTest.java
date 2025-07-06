/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.playeranswer;

import com.ssta.quiz.player.Player;
import com.ssta.quiz.question.Question;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAnswerTest {

  @Test
  void testPlayerAnswerBuilderAndGetters() {
    // Setup
    Player player = new Player();
    player.setId(1L);
    player.setDisplayName("TestPlayer");

    Question question = new Question();
    question.setId(2L);
    question.setQuestionText("What is the capital of France?");

    Integer answerOption = 2;
    boolean correct = true;
    Integer responseTimeMs = 5000;
    Integer score = 100;
    Instant now = Instant.now();

    // Execute
    PlayerAnswer playerAnswer = PlayerAnswer.builder()
        .player(player)
        .question(question)
        .answerOption(answerOption)
        .correct(correct)
        .responseTimeMs(responseTimeMs)
        .score(score)
        .createdAt(now)
        .build();

    // Verify
    assertEquals(player, playerAnswer.getPlayer());
    assertEquals(question, playerAnswer.getQuestion());
    assertEquals(answerOption, playerAnswer.getAnswerOption());
    assertTrue(playerAnswer.isCorrect());
    assertEquals(responseTimeMs, playerAnswer.getResponseTimeMs());
    assertEquals(score, playerAnswer.getScore());
    assertEquals(now, playerAnswer.getCreatedAt());
  }

  @Test
  void testEqualsAndHashCode() {
    // Setup
    PlayerAnswer playerAnswer1 = new PlayerAnswer();
    playerAnswer1.setId(1L);

    PlayerAnswer playerAnswer2 = new PlayerAnswer();
    playerAnswer2.setId(1L);

    PlayerAnswer playerAnswer3 = new PlayerAnswer();
    playerAnswer3.setId(2L);

    // Verify equality based on ID
    assertEquals(playerAnswer1, playerAnswer2);
    assertNotEquals(playerAnswer1, playerAnswer3);

    // Verify hashCode consistency with equals
    assertEquals(playerAnswer1.hashCode(), playerAnswer2.hashCode());
    assertNotEquals(playerAnswer1.hashCode(), playerAnswer3.hashCode());
  }

  @Test
  void testToString() {
    // Setup
    PlayerAnswer playerAnswer = new PlayerAnswer();
    playerAnswer.setId(1L);
    playerAnswer.setAnswerOption(2);
    playerAnswer.setCorrect(true);
    playerAnswer.setResponseTimeMs(5000);
    playerAnswer.setScore(100);

    Player player = new Player();
    player.setId(10L);
    Question question = new Question();
    question.setId(20L);

    playerAnswer.setPlayer(player);
    playerAnswer.setQuestion(question);

    // Execute
    String toStringResult = playerAnswer.toString();

    // Verify that toString doesn't include player and question details
    // This checks that @ToString(exclude={"player", "question"}) is working
    assertFalse(toStringResult.contains("player="));
    assertFalse(toStringResult.contains("question="));

    // But does include other fields
    assertTrue(toStringResult.contains("id=1"));
    assertTrue(toStringResult.contains("answerOption=2"));
    assertTrue(toStringResult.contains("correct=true"));
    assertTrue(toStringResult.contains("responseTimeMs=5000"));
    assertTrue(toStringResult.contains("score=100"));
  }
}
