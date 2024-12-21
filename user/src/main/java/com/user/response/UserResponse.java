package com.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.entity.User;

public class UserResponse {
  private final String id;

  private final String username;

  @JsonProperty("first_name")
  private final String firstName;

  @JsonProperty("last_name")
  private final String lastName;

  public UserResponse(User user) {
    this.id = user.getId().toString();
    this.username = user.getUsername();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
