package com.ecommerceapp.authenticationservice.user.response;

public record LoginResponse(
        Long id,
        String username,
        String email,
        String token
) {
}
