package user.adapter.api.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import user.adapter.api.validation.validator.FollowsUserValidator;

@Constraint(validatedBy = FollowsUserValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FollowsUser {
  String message() default "Already following given user";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  boolean shouldFollow() default true;
}
