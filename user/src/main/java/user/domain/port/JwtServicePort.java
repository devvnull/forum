package user.domain.port;

import org.springframework.security.core.userdetails.UserDetails;
import user.domain.model.User;

public interface JwtServicePort {
  String generateAccessToken(User user);

  String generateRefreshToken(User user);

  String extractUserId(String token);

  String extractUsername(String token);

  boolean validateToken(String token, UserDetails userDetails);
}
