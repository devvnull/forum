package com.user.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {
  @Id
  @Column(columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true)
  private String username;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String password;

  @CreatedDate
  @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public User(String username, String firstName, String lastName, String password) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }

  public User() {}

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("USER"));
  }
}
