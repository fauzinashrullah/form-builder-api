package com.yourstechnology.formbuilder.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "The email must be a valid email address.")
    @NotBlank(message = "The email must be a valid email address.")
    private String email;

    @NotBlank(message = "The password field is required.")
    @Size(min = 5, message = "The password field is required.")
    private String password;
}
