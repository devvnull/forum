package user.adapter.api.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import user.adapter.api.validation.validator.NotSelfReferenceValidator;

@Constraint(validatedBy = NotSelfReferenceValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSelfReference {
  String message() default "Provided user ID must not be the same as the authorized user ID";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
