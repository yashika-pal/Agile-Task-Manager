package com.smartfocus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {

  public static class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    public String username;

    @NotBlank
    @Size(min = 4, max = 100)
    public String password;
  }

  public static class LoginRequest {
    @NotBlank
    public String username;

    @NotBlank
    public String password;
  }

  public static class AuthResponse {
    public Long id;
    public String username;
  }
}

