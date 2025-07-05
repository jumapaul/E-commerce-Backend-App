package com.ecommerceapp.authenticationservice.user.dtos;

public record LoginRequest(
        String email,
        String password
) {
}
