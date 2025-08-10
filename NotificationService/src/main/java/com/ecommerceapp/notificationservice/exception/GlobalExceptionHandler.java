package com.ecommerceapp.notificationservice.exception;

import com.ecommerceapp.notificationservice.notification.dto.ApiResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MessagingException.class)
    ResponseEntity<Object> handleMessagingException(MessagingException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                null
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleGenericException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                null
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
