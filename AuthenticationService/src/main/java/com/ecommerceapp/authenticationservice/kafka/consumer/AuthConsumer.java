package com.ecommerceapp.authenticationservice.kafka.consumer;

import com.ecommerceapp.authenticationservice.email.EmailService;
import com.ecommerceapp.authenticationservice.kafka.AuthConfirmation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "auth-topic", groupId = "authGroup")
    public void consumeAuthConfirmation(AuthConfirmation confirmation) throws MessagingException {
        log.info("==========>consuming the auth message: {}", confirmation);

        emailService.sendEmailVerificationCode(confirmation.email(), confirmation.code());
    }
}
