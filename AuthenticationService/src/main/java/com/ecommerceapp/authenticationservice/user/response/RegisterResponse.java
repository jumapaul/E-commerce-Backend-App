package com.ecommerceapp.authenticationservice.user.response;

public record RegisterResponse(
        String username,
        String email,
        String role,
        String verificationCode
) {
}
