package com.ecommerceapp.shoppingcartservice.shoppingCart.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
