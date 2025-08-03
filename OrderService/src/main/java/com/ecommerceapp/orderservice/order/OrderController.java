package com.ecommerceapp.orderservice.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<Order> makeOrder(
            @PathVariable(name = "userId") Long userId,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(orderService.makeOrder(userId, authHeader));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("update/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable(name = "orderId") String orderId,
            @RequestParam(name = "status") String status
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    @GetMapping("status/{userId}")
    public ResponseEntity<ApiResponse<List<Order>>> getUserOrderBasedOnStatus(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "status") String status
    ) {
        return ResponseEntity.ok(orderService.getUserOrderBasedOnStatus(userId, status));
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersBasedOnStatus(
            @RequestParam(name = "status") String status
    ) {
        return ResponseEntity.ok(orderService.getOrdersBasedOnStatus(status));
    }
}