package com.ecommerceapp.orderservice.config;


import com.ecommerceapp.orderservice.order.*;
import com.ecommerceapp.orderservice.payment.InternalStkPushRequest;
import com.ecommerceapp.orderservice.service.OrderStatusPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class OrderStatusUpdateHandler {

    private final OrderRepository orderRepository;
    private final OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public void updateOrder(String id, Consumer<Order> consumer, OrderRequestDto orderRequestDto) {
        orderRepository.findById(id).ifPresent(order -> {
            consumer.accept(order);
            updateOrder(order, orderRequestDto);
            orderRepository.save(order);
        });
    }

    private void updateOrder(Order order, OrderRequestDto orderRequestDto) {
        boolean isPaymentComplete = PaymentStatus.COMPLETED.equals(order.getPaymentStatus());

        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.COMPLETED : OrderStatus.CANCELLED;

        order.setOrderStatus(orderStatus);

        if (!isPaymentComplete) {
            orderStatusPublisher.publisherOrderEvent(fromEntity(order, orderRequestDto), orderStatus, orderRequestDto);
        }
    }

    public InternalStkPushRequest fromEntity(Order order, OrderRequestDto orderRequestDto) {

        InternalStkPushRequest newRequest = new InternalStkPushRequest();
        newRequest.setPhoneNumber(orderRequestDto.phoneNumber());
        newRequest.setOrderId(order.getId());
        newRequest.setAmount(order.getTotalPrice());

        return newRequest;
    }
}
