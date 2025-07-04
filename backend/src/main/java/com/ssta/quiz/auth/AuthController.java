/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.auth;

import com.ssta.quiz.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @GetMapping("/status")
  public ApiResponse<String> getAuthStatus() {
    // Placeholder for auth status endpoint
    // Will be implemented with Twitch OAuth later
    return ApiResponse.success("Authentication system ready");
  }
}
