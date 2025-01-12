package user.application.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.adapter.security.JwtService;
import user.domain.model.User;
import user.domain.port.UserRepositoryPort;

@Service
public class UserService {
  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final UserRepositoryPort userRepositoryPort;
  private final PasswordEncoder passwordEncoder;

  public UserService(
      JwtService jwtService,
      AuthenticationManager authManager,
      UserRepositoryPort userRepositoryPort,
      PasswordEncoder passwordEncoder) {
    this.jwtService = jwtService;
    this.authManager = authManager;
    this.userRepositoryPort = userRepositoryPort;
    this.passwordEncoder = passwordEncoder;
  }

  public User create(String username, String firstName, String lastName, String password) {
    String hashedPassword = passwordEncoder.encode(password);
    User user = new User(null, username, firstName, lastName, hashedPassword, null, null);
    return userRepositoryPort.save(user);
  }

  public Optional<User> findByUsername(String username) {
    return userRepositoryPort.findByUsername(username);
  }

  public Optional<User> findById(UUID id) {
    return userRepositoryPort.findById(id);
  }

  public boolean existsByUsername(String username) {
    return userRepositoryPort.existsByUsername(username);
  }

  public boolean verifyPassword(String plainPassword, User user) {
    return passwordEncoder.matches(plainPassword, user.getPassword());
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
