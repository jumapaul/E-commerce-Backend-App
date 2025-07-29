package com.ecommerceapp.shoppingcartservice.shoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timeStamp;

    public ApiResponse(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }
}
