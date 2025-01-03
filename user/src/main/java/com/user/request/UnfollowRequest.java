package com.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.annotation.FollowsUser;
import com.user.annotation.NotSelfReference;
import com.user.annotation.UUIDFormat;
import com.user.annotation.UserIDExists;
import jakarta.validation.constraints.NotNull;

public class UnfollowRequest {
  @JsonProperty("user_id")
  @NotNull(message = "{user_id.required}")
  @UUIDFormat
  @UserIDExists
  @NotSelfReference
  @FollowsUser(message = "{user.not_followed}")
  private String userId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
