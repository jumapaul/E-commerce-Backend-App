package com.example.e_commerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InternalStkPushRequest {
    @JsonProperty("Amount")
    private double amount;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
    @JsonProperty("orderId")
    private String orderId;
}
