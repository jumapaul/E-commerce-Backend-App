package com.ecommerceapp.orderservice.cart_client;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingCartResponse(
        Long userId,
        String userEmail,
        String username,
        BigDecimal totalPrice,
        List<CartItem> cartItems
) {
}
