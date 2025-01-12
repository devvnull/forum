package user.adapter.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;
import user.adapter.api.validation.annotation.UUIDFormat;

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
