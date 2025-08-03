package com.ecommerceapp.orderservice.order;

import com.ecommerceapp.orderservice.cart_client.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
@Builder
public class Order {
    @Id
    private String id;
    private Status status;
    private Long userId;
    private BigDecimal totalPrice;
    private List<CartItem> cartItems;
}
