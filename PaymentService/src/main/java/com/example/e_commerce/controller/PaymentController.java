package com.example.e_commerce.controller;

import com.example.e_commerce.dtos.*;
import com.example.e_commerce.event.PaymentEvent;
import com.example.e_commerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/mpesa-token")
    public ResponseEntity<RequestTokenResponse> getMpesaToken() {
        return ResponseEntity.ok(paymentService.getToken());
    }

    @PostMapping("initiate-stk")
    public ResponseEntity<String> initiatePush(
            @RequestBody InternalStkPushRequest request
    ) {
        paymentService.initiateStkPush(request);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/transaction-result")
    public ResponseEntity<PaymentEvent> transactionResults(
            @RequestBody MpesaStkPushResults callback
    ) {

        return ResponseEntity.ok(paymentService.processCallback(callback));
    }


}
