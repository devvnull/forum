package user.adapter.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import user.adapter.api.request.SignUpRequest;
import user.adapter.api.validation.annotation.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    SignUpRequest userRequest = (SignUpRequest) value;

    return userRequest.getPassword() != null
        && userRequest.getPassword().equals(userRequest.getPasswordConfirm());
  }
}
