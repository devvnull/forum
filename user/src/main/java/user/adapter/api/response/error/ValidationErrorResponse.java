package user.adapter.api.response.error;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse extends BaseErrorResponse {
  public static final String MESSAGE = "Validation failed";

  public ValidationErrorResponse(List<String> errors) {
    super(MESSAGE, errors);
  }

  public ValidationErrorResponse(String error) {
    super(MESSAGE, new ArrayList<>(List.of(error)));
  }

  public ValidationErrorResponse() {
    super(MESSAGE, new ArrayList<>());
  }
}
