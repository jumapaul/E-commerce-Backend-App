package com.ecommerceapp.authenticationservice.user.dtos;

public record RegisterRequest(
        String username,
        String password,
        String email
) {
}
