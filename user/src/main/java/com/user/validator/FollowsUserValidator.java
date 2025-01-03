package com.user.validator;

import com.user.annotation.FollowsUser;
import com.user.entity.User;
import com.user.service.FollowerService;
import com.user.service.JwtService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class FollowsUserValidator implements ConstraintValidator<FollowsUser, String> {
  private boolean shouldFollow;

  @Override
  public void initialize(FollowsUser constraintAnnotation) {
    this.shouldFollow = constraintAnnotation.shouldFollow();
  }

  @Autowired private FollowerService followerService;

  @Autowired private JwtService jwtService;

  @Override
  public boolean isValid(String id, ConstraintValidatorContext context) {
    if (id == null || id.isEmpty()) {
      return true;
    }

    User authUser = jwtService.getAuthUser();

    boolean follows = followerService.isFollowing(authUser.getId(), UUID.fromString(id));

    return follows == shouldFollow;
  }
}
