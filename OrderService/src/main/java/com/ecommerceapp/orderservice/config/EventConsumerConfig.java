package com.ecommerceapp.orderservice.config;


import com.ecommerceapp.orderservice.events.PaymentEvent;
import com.ecommerceapp.orderservice.order.OrderRequestDto;
import com.ecommerceapp.orderservice.order.PaymentStatus;
import com.ecommerceapp.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EventConsumerConfig {

    private final OrderStatusUpdateHandler handler;
    private final OrderService orderService;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {


        return (payment) -> {
            log.info("--------------->Payment status is: {}", payment.getPaymentStatus());
            if (payment.getPaymentStatus() == PaymentStatus.COMPLETED) {
                OrderRequestDto orderRequestDto = new OrderRequestDto(
                        payment.getPaymentNumber()
                );
                //update inventory
                handler.updateOrder(
                        payment.getOrderId(),
                        order -> order.setPaymentStatus(payment.getPaymentStatus()),
                        orderRequestDto);
            } else {
                cancelOrder(payment.getOrderId());
            }
        };
    }

    private void cancelOrder(String orderId) {
        orderService.cancelOrder(orderId);
    }
}
