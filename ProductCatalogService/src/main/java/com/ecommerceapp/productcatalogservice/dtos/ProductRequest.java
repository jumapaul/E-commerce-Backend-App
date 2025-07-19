package com.ecommerceapp.productcatalogservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        String productId,
        @NotEmpty(message = "Product name is required")
        String productName,
        String productDescription,
        @Positive(message = "Define available quantity")
        int availableQuantity,
        String itemImageUrl,
        @Positive(message = "Product price is required")
        double itemPrice,
        String productCategory
) {
}
