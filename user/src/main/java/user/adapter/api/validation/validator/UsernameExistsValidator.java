package user.adapter.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import user.adapter.api.validation.annotation.UsernameExists;
import user.application.service.UserService;

public class UsernameExistsValidator implements ConstraintValidator<UsernameExists, String> {
  private final UserService userService;

  @Autowired
  public UsernameExistsValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    if (username == null || username.isEmpty()) {
      return true;
    }

    return !this.userService.existsByUsername(username);
  }
}
