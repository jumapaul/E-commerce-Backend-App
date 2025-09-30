package com.example.e_commerce.event;

public record PaymentRequestDto(
        String orderId,
        Long userId,
        Long amount
) {
}
