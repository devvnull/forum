package com.user.validator;

import com.user.annotation.PasswordMatches;
import com.user.request.SignUpRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        SignUpRequest userRequest = (SignUpRequest) value;

        return userRequest.getPassword() != null && userRequest.getPassword().equals(userRequest.getPasswordConfirm());
    }
}
