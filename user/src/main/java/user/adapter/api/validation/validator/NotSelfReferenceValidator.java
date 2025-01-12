package user.adapter.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import user.adapter.api.validation.annotation.NotSelfReference;
import user.adapter.security.JwtService;
import user.domain.model.User;

public class NotSelfReferenceValidator implements ConstraintValidator<NotSelfReference, String> {
  @Autowired private JwtService jwtService;

  @Override
  public boolean isValid(String id, ConstraintValidatorContext context) {
    if (id == null || id.isEmpty()) {
      return true;
    }

    User user = jwtService.getAuthUser();

    return !user.getId().toString().equals(id);
  }
}
