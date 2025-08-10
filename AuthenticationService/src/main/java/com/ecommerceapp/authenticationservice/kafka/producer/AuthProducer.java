package com.ecommerceapp.authenticationservice.kafka.producer;

import com.ecommerceapp.authenticationservice.kafka.AuthConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthProducer {

    private final KafkaTemplate<String, AuthConfirmation> kafkaTemplate;

    public void sendVerificationCode(AuthConfirmation confirmation) {
        try {
            log.info("============>code: {}", confirmation.code());

            Message<AuthConfirmation> message = MessageBuilder
                    .withPayload(confirmation)
                    .setHeader(KafkaHeaders.TOPIC, "auth-topic")
                    .build();

            kafkaTemplate.send(message);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
