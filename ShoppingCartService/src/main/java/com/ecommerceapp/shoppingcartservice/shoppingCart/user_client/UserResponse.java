package com.ecommerceapp.authenticationservice.user.dtos;

public record UserResponse(
        String username,
        String email,
        String role
) {
}
