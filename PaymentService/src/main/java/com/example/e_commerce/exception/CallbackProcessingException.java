package com.example.e_commerce.exception;

public class CallbackProcessingException extends RuntimeException{
    public CallbackProcessingException(String message, Throwable cause){
        super(message, cause);
    }
}
