package com.ecommerceapp.orderservice.order;

import com.ecommerceapp.orderservice.cart_client.CartItem;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String id,
        Status status,
        String userEmail,
        BigDecimal totalPrice,
        List<CartItem>cartItems
) {
}
