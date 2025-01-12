package user.adapter.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import user.adapter.api.validation.annotation.FollowsUser;
import user.adapter.api.validation.annotation.NotSelfReference;
import user.adapter.api.validation.annotation.UUIDFormat;
import user.adapter.api.validation.annotation.UserIDExists;

public class FollowRequest {
  @JsonProperty("user_id")
  @NotNull(message = "{user_id.required}")
  @UUIDFormat
  @UserIDExists
  @NotSelfReference
  @FollowsUser(shouldFollow = false)
  private String userId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
