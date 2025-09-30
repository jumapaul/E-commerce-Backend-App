package com.example.e_commerce.service;

import com.example.e_commerce.config.MpesaConfigurations;
import com.example.e_commerce.dtos.*;
import com.example.e_commerce.event.OrderEvent;
import com.example.e_commerce.event.PaymentEvent;
import com.example.e_commerce.event.PaymentStatus;
import com.example.e_commerce.exception.CallbackProcessingException;
import com.example.e_commerce.exception.ResourceNotFoundException;
import com.example.e_commerce.mpesa_client.MpesaClient;
import com.example.e_commerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Sinks;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final MpesaClient mpesaClient;
    private final MpesaConfigurations configurations;
    private final PaymentRepository paymentRepository;
    private final Sinks.Many<PaymentEvent> paymentSink;

    @Override
    public RequestTokenResponse getToken() {
        String credential = String.format("%s:%s", configurations.getConsumerKey(), configurations.getConsumerSecret());
        String encodedCredentials = Base64.getEncoder().encodeToString(credential.getBytes());
        String basicAuth = String.format("%s %s", "Basic", encodedCredentials);

        return mpesaClient.getToken(configurations.getGrantType(), basicAuth);
    }

    @Override
    public PaymentsEntity initiateStkPush(InternalStkPushRequest request) {

        try {
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String password = getPassword(configurations.getShortCode(), configurations.getPassKey(), timestamp);
            MpesaStkPushRequest request1 = getMpesaStkPushRequest(request, timestamp, password);
            RequestTokenResponse response = getToken();
            String authToken = String.format("%s %s", "Bearer", response.getAccessToken());

            MpesaStkPushResponse response1 = mpesaClient.stkPushRequest(request1, authToken);

            PaymentsEntity paymentsEntity = PaymentsEntity.builder()
                    .orderId(request.getOrderId())
                    .mpesaReceipt(null)
                    .checkOutRequestId(response1.getCheckoutRequestID())
                    .transactionDate(null)
                    .phoneNumber(request.getPhoneNumber())
                    .status(PaymentStatus.PENDING)
                    .amount(request.getAmount())
                    .build();

            return paymentRepository.save(paymentsEntity);

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private MpesaStkPushRequest getMpesaStkPushRequest(InternalStkPushRequest request, String timestamp, String password) {

        return MpesaStkPushRequest.builder()
                .transactionType("CustomerPayBillOnline")
                .amount(request.getAmount())
                .callBackURL(configurations.getCallbackUrl())
                .phoneNumber(request.getPhoneNumber())
                .partyA(request.getPhoneNumber())
                .partyB(configurations.getShortCode())
                .accountReference("CompanyXLTD")
                .transactionDesc("Payment of X")
                .timestamp(timestamp)
                .password(password)
                .businessShortCode(configurations.getShortCode())
                .build();
    }

    private String getPassword(String shortCode, String passKey, String timeStamp) {
        String value = shortCode + passKey + timeStamp;

        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    @Transactional
    public PaymentEvent processCallback(MpesaStkPushResults callback) {
        try {
            PaymentsEntity paymentsEntity = paymentRepository.findByCheckOutRequestId(
                    callback.getBody().getStkCallback().getCheckoutRequestID()).orElseThrow(() ->
                    new ResourceNotFoundException("Transaction not found")
            );
            StkCallback stkCallback = callback.getBody().getStkCallback();

            PaymentsEntity updatedPayment = (stkCallback.getResultCode() == 0)
                    ? handleSuccessfulTransaction(stkCallback, paymentsEntity)
                    : handleFailedTransaction(paymentsEntity);

            PaymentEvent event = new PaymentEvent(
                    updatedPayment.getStatus(),
                    updatedPayment.getCheckOutRequestId(),
                    updatedPayment.getPhoneNumber(),
                    updatedPayment.getOrderId()
            );

            paymentSink.tryEmitNext(event);

            return event;

        } catch (Exception e) {
            throw new CallbackProcessingException("Failed to process callback", e);
        }
    }


    private PaymentsEntity handleSuccessfulTransaction(StkCallback stkCallback, PaymentsEntity paymentsEntity) {
        try {
            CallbackResults callbackResults = extractResults(stkCallback);
            paymentsEntity.setMpesaReceipt(callbackResults.getMpesaReceiptNumber());
            paymentsEntity.setTransactionDate(callbackResults.getTransactionDate());
            paymentsEntity.setPhoneNumber(callbackResults.getPhoneNumber());
            paymentsEntity.setStatus(PaymentStatus.COMPLETED);
            paymentsEntity.setAmount(callbackResults.getAmount());

            return paymentRepository.save(paymentsEntity);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private PaymentsEntity handleFailedTransaction(PaymentsEntity paymentsEntity) {

        try {
            paymentsEntity.setStatus(PaymentStatus.FAILED);
            paymentsEntity.setTransactionDate(LocalDateTime.now());
            return paymentRepository.save(paymentsEntity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private CallbackResults extractResults(StkCallback callback) {

        try {
            CallbackResults results = new CallbackResults();

            results.setMerchantRequestID(callback.getMerchantRequestID());
            results.setCheckoutRequestID(callback.getCheckoutRequestID());
            results.setResultCode(callback.getResultCode());
            results.setResultDesc(callback.getResultDesc());

            if (callback.getResultCode() == 0 && callback.getCallbackMetadata() != null) {
                List<CallbackMetadataItem> items = callback.getCallbackMetadata().getItem();

                for (CallbackMetadataItem item : items) {
                    switch (item.getName()) {
                        case "Amount" -> results.setAmount(Double.valueOf(item.getValue()));
                        case "MpesaReceiptNumber" -> results.setMpesaReceiptNumber(item.getValue());
                        case "TransactionDate" -> {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                            results.setTransactionDate(LocalDateTime.parse(item.getValue(), formatter));
                        }
                        case "PhoneNumber" -> results.setPhoneNumber(item.getValue());
                        default -> log.debug("Unknown metadata item: {} - {}", item.getName(), item.getValue());
                    }
                }
            }

            return results;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void cancelOrder(OrderEvent orderEvent) {

    }
}
