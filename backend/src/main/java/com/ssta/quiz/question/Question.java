/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.question;

import com.ssta.quiz.quiz.Quiz;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

  @Id
  private UUID id;

  @Column(name = "quiz_id", nullable = false)
  private UUID quizId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quiz_id", insertable = false, updatable = false)
  private Quiz quiz;

  @Column(name = "question_text", nullable = false)
  private String questionText;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "time_limit", nullable = false)
  private Integer timeLimit;

  @Column(name = "order_index", nullable = false)
  private Integer orderIndex;

  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;
}
