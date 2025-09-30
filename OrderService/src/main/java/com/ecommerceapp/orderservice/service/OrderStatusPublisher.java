package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.orderservice.events.OrderEvent;
import com.ecommerceapp.orderservice.order.OrderRequestDto;
import com.ecommerceapp.orderservice.order.OrderStatus;
import com.ecommerceapp.orderservice.payment.InternalStkPushRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Slf4j
@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    public void publisherOrderEvent(
            InternalStkPushRequest internalStkPushRequest,
            OrderStatus orderStatus, OrderRequestDto orderRequestDto) {

        OrderEvent orderEvent = new OrderEvent(internalStkPushRequest, orderStatus, orderRequestDto);

        orderSinks.tryEmitNext(orderEvent);
    }
}
