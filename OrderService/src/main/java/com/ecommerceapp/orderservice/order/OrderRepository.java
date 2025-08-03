package com.ecommerceapp.orderservice.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByStatus(String status);

    List<Order> findByUserIdAndStatus(Long userId, String status);
}
