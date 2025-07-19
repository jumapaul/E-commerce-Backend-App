package com.ecommerceapp.productcatalogservice.dtos;

public record ProductResponse(
        String productId,
        String productName,
        String productDescription,
        int availableQuantity,
        String itemImageUrl,
        double itemPrice,
        String productCategory
) {
}
