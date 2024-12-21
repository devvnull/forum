package com.user.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.entity.User;
import com.user.repository.UserRepository;
import com.user.request.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SignUpTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;

  @Test
  void input_should_be_provided() throws Exception {
    mockMvc
        .perform(post("/api/signup").contentType("application/json").content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Validation failed"))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(
            jsonPath("$.errors").value(org.hamcrest.Matchers.hasItem("Username is required")))
        .andExpect(
            jsonPath("$.errors").value(org.hamcrest.Matchers.hasItem("Last name is required")))
        .andExpect(
            jsonPath("$.errors").value(org.hamcrest.Matchers.hasItem("First name is required")))
        .andExpect(
            jsonPath("$.errors").value(org.hamcrest.Matchers.hasItem("Password is required")))
        .andExpect(
            jsonPath("$.errors")
                .value(org.hamcrest.Matchers.hasItem("Password confirmation is required")))
        .andReturn();
  }

  @Test
  void username_should_be_in_between_5_and_50_characters_long() throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setUsername("user");
    String requestBody = objectMapper.writeValueAsString(signUpRequest);

    // too short
    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors")
                .value(
                    org.hamcrest.Matchers.hasItem(
                        "Username must be within 5 and 50 characters long")))
        .andReturn();

    signUpRequest.setUsername("u".repeat(51));
    requestBody = objectMapper.writeValueAsString(signUpRequest);

    // too long
    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors")
                .value(
                    org.hamcrest.Matchers.hasItem(
                        "Username must be within 5 and 50 characters long")))
        .andReturn();

    // size fits, error is not shown anymore
    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors")
                .value(
                    org.hamcrest.Matchers.not("Username must be within 5 and 50 characters long")))
        .andReturn();
  }

  @Test
  void username_should_be_alphanumeric() throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setUsername("xიუზერი");
    String requestBody = objectMapper.writeValueAsString(signUpRequest);

    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors")
                .value(org.hamcrest.Matchers.hasItem("Username must be alphanumeric")))
        .andReturn();
  }

  @Test
  void username_must_be_unique() throws Exception {
    String username = "fancyUser";
    this.userRepository.save(new User(username, "name", "name", "Password123!"));

    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setUsername(username);
    String requestBody = objectMapper.writeValueAsString(signUpRequest);

    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors").value(org.hamcrest.Matchers.hasItem("Username must be unique")))
        .andReturn();
  }

  @Test
  void password_should_be_in_between_8_and_250_characters_long() throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setPassword("passwor");
    String requestBody = objectMapper.writeValueAsString(signUpRequest);

    // too short
    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors")
                .value(
                    org.hamcrest.Matchers.hasItem(
                        "Password must be within 8 and 250 characters long")))
        .andReturn();

    signUpRequest.setPassword("p".repeat(251));
    requestBody = objectMapper.writeValueAsString(signUpRequest);

    // too long
    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors")
                .value(
                    org.hamcrest.Matchers.hasItem(
                        "Password must be within 8 and 250 characters long")))
        .andReturn();

    signUpRequest.setPassword("password");
    requestBody = objectMapper.writeValueAsString(signUpRequest);

    // size fits, error is not shown anymore
    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors")
                .value(
                    org.hamcrest.Matchers.not("Password must be within 8 and 250 characters long")))
        .andReturn();
  }

  @Test
  void
      password_should_contain_at_least_1_lower_case_and_uppercase_char_and_number_and_special_char()
          throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setPassword("password");
    String requestBody = objectMapper.writeValueAsString(signUpRequest);
    String message =
        "Password must contain at least one uppercase letter, one lowercase, one number, one special character";

    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").value(org.hamcrest.Matchers.hasItem(message)))
        .andReturn();

    signUpRequest.setPassword("password1");
    requestBody = objectMapper.writeValueAsString(signUpRequest);

    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").value(org.hamcrest.Matchers.hasItem(message)))
        .andReturn();

    signUpRequest.setPassword("password1!");
    requestBody = objectMapper.writeValueAsString(signUpRequest);

    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").value(org.hamcrest.Matchers.hasItem(message)))
        .andReturn();

    // Password is valid, we shouldn't see error anymore
    signUpRequest.setPassword("Password1!");
    requestBody = objectMapper.writeValueAsString(signUpRequest);

    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").value(org.hamcrest.Matchers.not(message)))
        .andReturn();
  }

  @Test
  void it_creates_a_user() throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setUsername("myUsername");
    signUpRequest.setFirstName("firstName");
    signUpRequest.setLastName("lastName");
    signUpRequest.setPassword("Password1123!");
    signUpRequest.setPasswordConfirm("Password1123!");
    String requestBody = objectMapper.writeValueAsString(signUpRequest);

    mockMvc
        .perform(post("/api/signup").contentType("application/json").content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("myUsername"))
        .andExpect(jsonPath("$.first_name").value("firstName"))
        .andExpect(jsonPath("$.last_name").value("lastName"))
        .andReturn();

    User user = this.userRepository.findByUsername(signUpRequest.getUsername());
    assertEquals("myUsername", user.getUsername());
    assertEquals("firstName", user.getFirstName());
    assertEquals("lastName", user.getLastName());
  }
}
