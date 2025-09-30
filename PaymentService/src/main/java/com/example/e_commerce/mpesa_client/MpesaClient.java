package com.example.e_commerce.mpesa_client;


import com.example.e_commerce.dtos.MpesaStkPushRequest;
import com.example.e_commerce.dtos.MpesaStkPushResponse;
import com.example.e_commerce.dtos.RequestTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "mpesa",
        url = "https://sandbox.safaricom.co.ke")
public interface MpesaClient {

    @GetMapping("/oauth/v1/generate")
    RequestTokenResponse getToken(
            @RequestParam(name = "grant_type") String grantType,
            @RequestHeader("Authorization") String authHeader
    );
    @PostMapping("/mpesa/stkpush/v1/processrequest")
    MpesaStkPushResponse stkPushRequest(
            @RequestBody MpesaStkPushRequest request,
            @RequestHeader("Authorization") String token
    );
    //generate token
    //make payment.
}
