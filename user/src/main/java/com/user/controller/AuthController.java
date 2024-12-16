package com.user.controller;

import com.user.entity.User;
import com.user.request.LoginRequest;
import com.user.request.SignUpRequest;
import com.user.response.UserResponse;
import com.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest request) {}

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        User user = this.userService.create(
            request.getUsername(),
            request.getFirstName(),
            request.getLastName(),
            request.getPassword()
        );

        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping("/logout")
    public void logout() {}

    @PostMapping("/refresh-token")
    public void refreshToken() {}
}
