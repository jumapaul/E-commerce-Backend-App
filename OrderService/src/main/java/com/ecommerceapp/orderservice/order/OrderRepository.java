package com.ecommerceapp.orderservice.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByOrderStatus(String status);

    List<Order> findByUserIdAndOrderStatus(Long userId, String status);
}
