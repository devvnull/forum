package user.adapter.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import user.adapter.api.request.contract.PasswordConfirmation;
import user.adapter.api.validation.annotation.PasswordMatches;
import user.adapter.api.validation.annotation.UsernameExists;

@PasswordMatches
public class SignUpRequest implements PasswordConfirmation {
  @NotNull(message = "{username.required}")
  @Size(min = 5, max = 50, message = "{username.size}")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{username.alphanumeric}")
  @UsernameExists(message = "{username.unique}")
  private String username;

  @NotNull(message = "{firstname.required}")
  @Size(max = 100, message = "{firstname.size}")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "{firstname.alpha}")
  @JsonProperty("first_name")
  private String firstName;

  @NotNull(message = "{lastname.required}")
  @Size(max = 100, message = "{lastname.size}")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "{lastname.alpha}")
  @JsonProperty("last_name")
  private String lastName;

  @NotNull(message = "{password.required}")
  @Size(min = 8, max = 250, message = "{password.size}")
  @Pattern(
      regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
      message = "{password.pattern}")
  private String password;

  @JsonProperty("password_confirm")
  @NotNull(message = "{password_confirm.required}")
  private String passwordConfirm;

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getPasswordConfirm() {
    return this.passwordConfirm;
  }

  public String getUsername() {
    return this.username;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
