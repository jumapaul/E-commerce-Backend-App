package com.ecommerceapp.shoppingcartservice.shoppingCart.cart;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    private Long userId;
    private String username;
    private String userEmail;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private List<CartItem> cartItems = new ArrayList<>();
}

