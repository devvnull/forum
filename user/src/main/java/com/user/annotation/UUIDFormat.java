package com.user.annotation;

import com.user.validator.UUIDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UUIDValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UUIDFormat {
  String message() default "Invalid UUID.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
