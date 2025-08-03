package com.ecommerceapp.orderservice.mapper;

import com.ecommerceapp.orderservice.cart_client.ShoppingCartResponse;
import com.ecommerceapp.orderservice.order.Order;
import com.ecommerceapp.orderservice.order.Status;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order fromCart(ShoppingCartResponse response) {
        return Order.builder()
                .status(Status.PENDING)
                .userId(response.userId())
                .totalPrice(response.totalPrice())
                .cartItems(response.cartItems())
                .build();
    }
}
