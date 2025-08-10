package com.ecommerceapp.notificationservice.notification.dto;

import java.math.BigDecimal;

public record CartItem(
        String productId,
        String productName,
        String productDescription,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
