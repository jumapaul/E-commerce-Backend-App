package com.ecommerceapp.shoppingcartservice.shoppingCart.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private String productId;
    private String productName;
    private String productDescription;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
