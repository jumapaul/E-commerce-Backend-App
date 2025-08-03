package com.ecommerceapp.orderservice.product_client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {
    private int status;
    private String message;
    @JsonProperty("data")
    private Product product;
}
