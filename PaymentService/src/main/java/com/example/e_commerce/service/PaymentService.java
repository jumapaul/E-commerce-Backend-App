package com.example.e_commerce.service;

import com.example.e_commerce.dtos.*;
import com.example.e_commerce.event.OrderEvent;
import com.example.e_commerce.event.PaymentEvent;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    RequestTokenResponse getToken();

    PaymentsEntity initiateStkPush(InternalStkPushRequest request);

    PaymentEvent processCallback(MpesaStkPushResults callback);

    void cancelOrder(OrderEvent orderEvent);
}
