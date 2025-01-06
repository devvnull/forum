package com.user.controller;

import com.user.entity.User;
import com.user.exception.ValidationException;
import com.user.request.LoginRequest;
import com.user.request.SignUpRequest;
import com.user.response.UserResponse;
import com.user.service.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public Map<String, String> login(@Valid @RequestBody LoginRequest request) {
    User user = userService.findByUsername(request.getUsername());

    if (user == null || !this.userService.verifyPassword(request.getPassword(), user)) {
      throw new ValidationException("Invalid username or password");
    }

    String token = userService.verify(user, request.getPassword());

    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    return response;
  }

  @PostMapping("/signup")
  public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequest request) {
    User user =
        this.userService.create(
            request.getUsername(),
            request.getFirstName(),
            request.getLastName(),
            request.getPassword());

    return ResponseEntity.ok(new UserResponse(user));
  }

  // @PostMapping("/logout")
  // public void logout() {}
}
