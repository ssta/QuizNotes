package com.ssta.quiz.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

  @Test
  void success_withDataOnly_shouldReturnSuccessfulResponse() {
    String testData = "Test Data";
    ApiResponse<String> response = ApiResponse.success(testData);

    assertTrue(response.isSuccess(), "Response should be successful");
    assertEquals("Success", response.getMessage(), "Message should be 'Success'");
    assertEquals(testData, response.getData(), "Data should match the input");
  }

  @Test
  void success_withMessageAndData_shouldReturnSuccessfulResponse() {
    String testMessage = "Custom success message";
    Integer testData = 123;
    ApiResponse<Integer> response = ApiResponse.success(testMessage, testData);

    assertTrue(response.isSuccess(), "Response should be successful");
    assertEquals(testMessage, response.getMessage(), "Message should match the custom message");
    assertEquals(testData, response.getData(), "Data should match the input");
  }

  @Test
  void error_withMessage_shouldReturnErrorResponse() {
    String errorMessage = "Something went wrong";
    ApiResponse<Object> response = ApiResponse.error(errorMessage);

    assertFalse(response.isSuccess(), "Response should be an error");
    assertEquals(errorMessage, response.getMessage(), "Message should match the error message");
    assertNull(response.getData(), "Data should be null for an error response");
  }
}