package com.user.response.error;

import java.util.ArrayList;
import java.util.List;

public class ServerErrorResponse extends BaseErrorResponse {
  public static final String MESSAGE = "Something went wrong, please try again";

  public ServerErrorResponse(List<String> errors) {
    super(MESSAGE, errors);
  }

  public ServerErrorResponse(String error) {
    super(MESSAGE, new ArrayList<>(List.of(error)));
  }

  public ServerErrorResponse() {
    super(MESSAGE, new ArrayList<>());
  }
}
