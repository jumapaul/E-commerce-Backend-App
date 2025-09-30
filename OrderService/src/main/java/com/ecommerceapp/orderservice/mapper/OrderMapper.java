package com.ecommerceapp.orderservice.mapper;

import com.ecommerceapp.orderservice.cart_client.ShoppingCartResponse;
import com.ecommerceapp.orderservice.order.OrderRequestDto;
import com.ecommerceapp.orderservice.order.Order;
import com.ecommerceapp.orderservice.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order fromCart(ShoppingCartResponse response, OrderRequestDto request) {
        return Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .userId(response.userId())
                .userEmail(response.userEmail())
                .username(response.username())
                .phoneNumber(request.phoneNumber())
                .totalPrice(response.totalPrice())
                .cartItems(response.cartItems())
                .build();
    }
}
