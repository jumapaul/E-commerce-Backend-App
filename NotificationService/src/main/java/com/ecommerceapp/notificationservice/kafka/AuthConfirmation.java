package com.ecommerceapp.notificationservice.kafka;

public record AuthConfirmation(
        String email,
        String code
) {
}
