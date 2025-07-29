package com.ecommerceapp.shoppingcartservice.shoppingCart.productClient;

public record ProductResponse(
        int status,
        String message,
        Data data
) {
}
