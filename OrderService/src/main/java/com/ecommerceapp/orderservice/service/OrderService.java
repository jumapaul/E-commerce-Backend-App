package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.orderservice.order.ApiResponse;
import com.ecommerceapp.orderservice.order.OrderRequestDto;
import com.ecommerceapp.orderservice.order.Order;

import java.util.List;

public interface OrderService {

    Order makeOrder(Long userId, OrderRequestDto orderRequest);

    List<Order> getAllOrders();

    Order updateOrderStatus(String orderId, String status);

    ApiResponse<List<Order>> getUserOrderBasedOnStatus(Long userId, String status);

    ApiResponse<List<Order>> getOrdersBasedOnStatus(String status);

    Order findOrderById(String orderId);

    ApiResponse<String> cancelOrder(String orderId);
}
