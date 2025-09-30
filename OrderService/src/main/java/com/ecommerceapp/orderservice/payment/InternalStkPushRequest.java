package com.ecommerceapp.orderservice.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InternalStkPushRequest {
    @JsonProperty("Amount")
    private BigDecimal amount;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
    @JsonProperty("orderId")
    private String orderId;
}
