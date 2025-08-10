package com.ecommerceapp.shoppingcartservice.shoppingCart.user_client;

public record UserResponse(
        String username,
        String email,
        String role
) {
}
