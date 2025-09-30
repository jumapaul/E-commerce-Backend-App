package com.example.e_commerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CallbackResults {
    private String merchantRequestID;
    private String checkoutRequestID;
    private Integer resultCode;
    private String resultDesc;
    private Double amount;
    private String mpesaReceiptNumber;
    private LocalDateTime transactionDate;
    private String phoneNumber;
}
