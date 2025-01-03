package com.user.validator;

import com.user.annotation.UserIDExists;
import com.user.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class UserIDExistsValidator implements ConstraintValidator<UserIDExists, String> {
  @Autowired private UserService userService;

  @Override
  public boolean isValid(String id, ConstraintValidatorContext context) {
    if (id == null || id.isEmpty()) {
      return true;
    }

    return userService.findById(UUID.fromString(id)).isPresent();
  }
}
