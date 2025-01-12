package user.adapter.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.adapter.api.exception.ValidationException;
import user.adapter.api.openapi.SwaggerBadRequest;
import user.adapter.api.openapi.SwaggerNoContent;
import user.adapter.api.openapi.SwaggerUnauthorizedAction;
import user.adapter.api.request.FollowRequest;
import user.adapter.api.request.UnfollowRequest;
import user.adapter.security.JwtService;
import user.application.service.FollowerService;
import user.application.service.UserService;
import user.domain.model.User;

@RestController
@RequestMapping("/api")
public class FollowerController {
  private final FollowerService followerService;
  private final UserService userService;
  private final JwtService jwtService;

  public FollowerController(
      FollowerService followerService, UserService userService, JwtService jwtService) {
    this.followerService = followerService;
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @Operation(
      summary = "Follows a specific user",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"Follower"})
  @SwaggerNoContent
  @SwaggerBadRequest
  @SwaggerUnauthorizedAction
  @PostMapping("/follow")
  public ResponseEntity<Void> follow(@Valid @RequestBody FollowRequest request) {
    User authUser = jwtService.getAuthUser();
    Optional<User> optional = this.userService.findById(UUID.fromString(request.getUserId()));

    if (optional.isEmpty()) {
      throw new ValidationException("User not found");
    }

    User influencer = optional.get();

    followerService.follow(authUser.getId(), influencer.getId());

    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Unfollows a specific user",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"Follower"})
  @SwaggerNoContent
  @SwaggerBadRequest
  @SwaggerUnauthorizedAction
  @PostMapping("/unfollow")
  public ResponseEntity<Void> unfollow(@Valid @RequestBody UnfollowRequest request) {
    User authUser = jwtService.getAuthUser();
    Optional<User> optional = this.userService.findById(UUID.fromString(request.getUserId()));

    if (optional.isEmpty()) {
      throw new ValidationException("User not found");
    }

    User influencer = optional.get();

    followerService.unfollow(authUser.getId(), influencer.getId());

    return ResponseEntity.noContent().build();
  }
}
