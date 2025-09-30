package com.ecommerceapp.orderservice.events;

import com.ecommerceapp.orderservice.order.OrderRequestDto;
import com.ecommerceapp.orderservice.order.OrderStatus;
import com.ecommerceapp.orderservice.payment.InternalStkPushRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@NoArgsConstructor
@Data
public class OrderEvent implements Event {

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private InternalStkPushRequest requestDto;
    private OrderStatus orderStatus;
    private OrderRequestDto orderRequestDto;


    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public OrderEvent(InternalStkPushRequest requestDto, OrderStatus orderStatus, OrderRequestDto orderRequestDto) {
        this.requestDto = requestDto;
        this.orderStatus = orderStatus;
        this.orderRequestDto = orderRequestDto;
    }
}
