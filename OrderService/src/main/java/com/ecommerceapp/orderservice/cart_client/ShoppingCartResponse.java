package com.ecommerceapp.orderservice.cart_client;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingCartResponse(
        Long userId,
        BigDecimal totalPrice,
        List<CartItem>cartItems
) {
}
