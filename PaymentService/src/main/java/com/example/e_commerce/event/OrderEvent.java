package com.example.e_commerce.event;

import com.example.e_commerce.dtos.InternalStkPushRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class OrderEvent implements Event {

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    @JsonProperty("requestDto")
    private InternalStkPushRequest internalStkPushRequest;
    @JsonProperty("orderRequestDto")
    private OrderRequestDto orderRequest;
    private OrderStatus orderStatus;

    public OrderEvent(InternalStkPushRequest internalRequestDto, OrderStatus orderStatus, OrderRequestDto orderRequestDto) {
        this.internalStkPushRequest = internalRequestDto;
        this.orderStatus = orderStatus;
        this.orderRequest = orderRequestDto;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }
}
