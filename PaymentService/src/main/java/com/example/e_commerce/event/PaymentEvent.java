package com.example.e_commerce.event;

import com.example.e_commerce.dtos.MpesaStkPushResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@NoArgsConstructor
@Data
public class PaymentEvent implements Event {

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private PaymentStatus paymentStatus;
    private String checkoutRequestID;
    private String paymentNumber;
    private String orderId;

    public PaymentEvent(PaymentStatus paymentStatus, String checkoutRequestID, String paymentNumber, String orderId) {
        this.paymentStatus = paymentStatus;
        this.orderId = orderId;
        this.checkoutRequestID = checkoutRequestID;
        this.paymentNumber = paymentNumber;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }
}
