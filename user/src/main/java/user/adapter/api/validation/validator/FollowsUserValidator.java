package user.adapter.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import user.adapter.api.validation.annotation.FollowsUser;
import user.adapter.security.JwtService;
import user.application.service.FollowerService;
import user.domain.model.User;

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
