package user.adapter.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.adapter.api.exception.ValidationException;
import user.adapter.api.openapi.SwaggerBadRequest;
import user.adapter.api.request.LoginRequest;
import user.adapter.api.request.SignUpRequest;
import user.adapter.api.response.AuthTokenResponse;
import user.adapter.api.response.UserResponse;
import user.application.service.UserService;
import user.domain.model.User;

@RestController
@RequestMapping("/api")
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @ApiResponse(
      responseCode = "200",
      content = @Content(schema = @Schema(implementation = AuthTokenResponse.class)))
  @Operation(
      summary = "Logs in user",
      tags = {"Auth"})
  @SwaggerBadRequest
  @PostMapping("/login")
  public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody LoginRequest request) {
    Optional<User> user = userService.findByUsername(request.getUsername());

    if (user.isEmpty() || !this.userService.verifyPassword(request.getPassword(), user.get())) {
      throw new ValidationException("Invalid username or password");
    }

    String token = userService.verify(user.get(), request.getPassword());
    return ResponseEntity.ok(new AuthTokenResponse(token));
  }

  @ApiResponse(
      responseCode = "200",
      content = @Content(schema = @Schema(implementation = UserResponse.class)))
  @Operation(
      summary = "Creates a new user",
      tags = {"Auth"})
  @SwaggerBadRequest
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
