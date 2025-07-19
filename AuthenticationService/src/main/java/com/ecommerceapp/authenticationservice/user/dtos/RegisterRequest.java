package com.ecommerceapp.authenticationservice.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @NotEmpty(message = "Username is required")
        String username,
        String password,
        @Email(message = "Invalid email format")
        String email
) {
}
