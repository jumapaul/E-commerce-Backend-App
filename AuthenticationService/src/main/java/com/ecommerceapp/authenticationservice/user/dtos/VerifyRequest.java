package com.ecommerceapp.authenticationservice.user.dtos;

public record VerifyRequest(
        String email,
        String verificationCode
) {
}
