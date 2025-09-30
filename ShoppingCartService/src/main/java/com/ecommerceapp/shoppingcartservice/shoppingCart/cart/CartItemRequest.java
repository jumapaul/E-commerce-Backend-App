package com.ecommerceapp.shoppingcartservice.shoppingCart.cart;

public record CartItemRequest(
        String productId,
        int quantity
) {
}
