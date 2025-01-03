package com.user.service;

import com.user.entity.User;
import com.user.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired private JwtService jwtService;

  @Autowired AuthenticationManager authManager;

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  public User create(String username, String firstName, String lastName, String password) {
    String hashedPassword = passwordEncoder.encode(password);
    User user = new User(username, firstName, lastName, hashedPassword);
    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  }

  public boolean existsByUsername(String username) {
    return this.userRepository.findByUsername(username) != null;
  }

  public boolean verifyPassword(String plainPassword, User user) {
    return this.passwordEncoder.matches(plainPassword, user.getPassword());
  }

  public String verify(User user, String plainPassword) {
    Authentication authentication =
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), plainPassword));
    if (authentication.isAuthenticated()) {
      return jwtService.generateAccessToken(user);
    } else {
      throw new RuntimeException("Could not verify token");
    }
  }
}
