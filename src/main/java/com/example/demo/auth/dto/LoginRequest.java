package com.example.demo.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "empty email")
    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "empty password")
    @Size(min = 8, max = 64, message = "too short or long password")
    private String password;

    @NotBlank(message = "empty nickname")
    @Size(min = 2, max = 10, message = "too short or long nickname")
    private String nickname;
}
