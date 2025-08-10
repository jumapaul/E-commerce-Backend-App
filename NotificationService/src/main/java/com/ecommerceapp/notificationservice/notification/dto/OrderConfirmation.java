package com.ecommerceapp.notificationservice.notification.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String id,
        Status status,
        String userEmail,
        BigDecimal totalPrice,
        List<CartItem> cartItems
) {
}
