package com.user.service;

import com.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access_token_expiration}")
  private String accessTokenExpiration;

  @Value("${jwt.refresh_token_expiration}")
  private String refreshTokenExpiration;

  private String generateToken(User user, int expiration) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getId().toString());

    return Jwts.builder()
        .claims()
        .add(claims)
        .subject(user.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .and()
        .signWith(getKey())
        .compact();
  }

  public String generateAccessToken(User user) {
    return generateToken(user, Integer.parseInt(accessTokenExpiration));
  }

  public String extractUserId(String token) {
    return extractClaim(token, claims -> claims.get("userId", String.class));
  }

  public String generateRefreshToken(User user) {
    return generateToken(user, Integer.parseInt(refreshTokenExpiration));
  }

  private SecretKey getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public User getAuthUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (User) authentication.getPrincipal();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  public boolean isTokenValid(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return (username.equals(extractedUsername) && !isTokenExpired(token));
  }

  private Claims extractAllClaims(String token) {
    try {
      return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    } catch (ExpiredJwtException ex) {
      throw ex;
    }
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String userName = extractUsername(token);
    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
}
