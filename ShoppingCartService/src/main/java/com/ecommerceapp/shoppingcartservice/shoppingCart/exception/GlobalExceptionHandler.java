package com.ecommerceapp.shoppingcartservice.shoppingCart.exception;

import com.ecommerceapp.shoppingcartservice.shoppingCart.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                null
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                null
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage(),
                null
        ), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SecurityException.class)
    ResponseEntity<Object> handleUnauthorizedException(SecurityException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage(),
                null
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<Object> handleInternalServerErrorException(RuntimeException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                null
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    ResponseEntity<Object> handleServiceUnavailableException(ServiceUnavailableException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                exception.getMessage(),
                null
        ), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
