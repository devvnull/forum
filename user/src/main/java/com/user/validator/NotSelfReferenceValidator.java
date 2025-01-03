package com.user.validator;

import com.user.annotation.NotSelfReference;
import com.user.entity.User;
import com.user.service.JwtService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

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
