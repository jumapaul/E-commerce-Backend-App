package com.ecommerceapp.orderservice.product_client;

import java.math.BigDecimal;

public record ProductRequest(
        String productId,
        String productName,
        String productDescription,
        int availableQuantity,
        String itemImageUrl,
        BigDecimal itemPrice,
        String productCategory
) {
}
