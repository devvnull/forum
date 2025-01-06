package com.user.response.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BaseErrorResponse {
  private final String message;
  private final List<String> errors;

  public BaseErrorResponse(String message, List<String> errors) {
    this.message = message;
    this.errors = errors;
  }

  public Map<String, Object> body() {
    Map<String, Object> body = new HashMap<>();
    body.put("message", message);
    body.put("errors", errors);
    return body;
  }

  public void addError(String error) {
    errors.add(error);
  }

  public void addErrors(List<String> errors) {
    this.errors.addAll(errors);
  }
}
