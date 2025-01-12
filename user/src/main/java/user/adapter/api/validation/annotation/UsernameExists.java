package user.adapter.api.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import user.adapter.api.validation.validator.UsernameExistsValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameExistsValidator.class)
public @interface UsernameExists {
  String message() default "Username does not exist";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
