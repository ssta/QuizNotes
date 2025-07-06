/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.playeranswer;

import com.ssta.quiz.player.Player;
import com.ssta.quiz.question.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * Entity representing a player's answer to a quiz question.
 * Maps to the player_answers table in the database.
 */
@Entity
@Table(name = "player_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"player", "question"})
public class PlayerAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id", nullable = false)
  private Player player;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;

  @Column(name = "answer_option", nullable = false)
  private int answerOption;

  @Column(name = "correct", nullable = false)
  private boolean correct = false;

  /* Null means the time ran out without receiving a response */
  @Column(name = "response_time_ms")
  private Integer responseTimeMs;

  /* Null mean that the question has not yet been scored.  Zero means that the score is zero */
  private Integer score;

  @CreationTimestamp
  @Column(name = "created_at")
  private Instant createdAt;
}
