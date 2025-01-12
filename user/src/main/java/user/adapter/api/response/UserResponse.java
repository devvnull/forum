package user.adapter.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import user.domain.model.User;

public record UserResponse(
    UUID id,
    String username,
    @JsonProperty("first_name") String firstName,
    @JsonProperty("last_name") String lastName) {

  public UserResponse(User user) {
    this(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
  }
}
