/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an option for a multiple-choice question.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOption {
  private String text;
  private boolean correct;
}
