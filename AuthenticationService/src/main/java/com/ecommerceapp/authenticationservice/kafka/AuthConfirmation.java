package com.ecommerceapp.authenticationservice.kafka;

public record AuthConfirmation(
        String email,
        String code
) {
}
