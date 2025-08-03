package com.ecommerceapp.orderservice.product_client;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId;
    private String productName;
    private String productDescription;
    private int availableQuantity;
    private String itemImageUrl;
    private BigDecimal itemPrice;
    private String productCategory;
}
