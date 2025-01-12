package user.adapter.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequest {
  @NotNull(message = "{username.required}")
  @Size(min = 5, max = 50, message = "{username.size}")
  private String username;

  @NotNull(message = "{password.required}")
  private String password;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
