package com.ecommerceapp.orderservice.order;

import java.util.List;

public interface OrderService {

    Order makeOrder(Long userId, String authHeader);

    List<Order> getAllOrders();

    Order updateOrderStatus(String orderId, String status);

    ApiResponse<List<Order>> getUserOrderBasedOnStatus(Long userId, String status);

    ApiResponse<List<Order>> getOrdersBasedOnStatus(String status);

    Order findOrderById(String orderId);
}
