package com.ecommerceapp.shoppingcartservice.shoppingCart.productClient;

import java.math.BigDecimal;

public record Data(
        String productId,
        String productName,
        String productDescription,
        int availableQuantity,
        String itemImageUrl,
        BigDecimal itemPrice,
        String productCategory
) {
}
