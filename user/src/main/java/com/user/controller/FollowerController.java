package com.user.controller;

import com.user.entity.User;
import com.user.exception.ValidationException;
import com.user.request.FollowRequest;
import com.user.request.UnfollowRequest;
import com.user.service.FollowerService;
import com.user.service.JwtService;
import com.user.service.UserService;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FollowerController {
  @Autowired private FollowerService followerService;
  @Autowired private UserService userService;
  @Autowired private JwtService jwtService;

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
