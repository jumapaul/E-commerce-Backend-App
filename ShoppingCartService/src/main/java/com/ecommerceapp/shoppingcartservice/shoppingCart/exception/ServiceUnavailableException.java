package com.ecommerceapp.shoppingcartservice.shoppingCart.exception;

public class ServiceUnavailableException extends RuntimeException{
    public ServiceUnavailableException(String message){
        super(message);
    }
}
