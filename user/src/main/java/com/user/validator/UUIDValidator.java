package com.user.validator;

import com.user.annotation.UUIDFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<UUIDFormat, String> {
  @Override
  public boolean isValid(String uuid, ConstraintValidatorContext context) {
    if (uuid == null || uuid.isEmpty()) {
      return true;
    }

    try {
      UUID.fromString(uuid);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
