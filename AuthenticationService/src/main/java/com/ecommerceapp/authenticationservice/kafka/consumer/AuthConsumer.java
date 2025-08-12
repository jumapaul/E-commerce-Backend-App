package com.ecommerceapp.authenticationservice.kafka.consumer;

import com.ecommerceapp.authenticationservice.email.EmailService;
import com.ecommerceapp.authenticationservice.kafka.AuthConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "auth-topic", groupId = "java-group-1")
    public void consumeAuthConfirmation(AuthConfirmation confirmation) {

        try {
            emailService.sendEmailVerificationCode(confirmation.email(), confirmation.code());
        } catch (Exception e) {
            log.info("================>exception: {}", e.getMessage());
        }
    }
}
