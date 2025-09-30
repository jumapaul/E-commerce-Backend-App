package com.ecommerceapp.orderservice.payment;

public record PaymentRequestDto(
        String orderId,
        Long userId,
        Long amount
) {
}
