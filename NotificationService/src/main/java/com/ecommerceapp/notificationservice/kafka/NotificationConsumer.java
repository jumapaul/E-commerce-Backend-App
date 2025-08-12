package com.ecommerceapp.notificationservice.kafka;

import com.ecommerceapp.notificationservice.email.EmailService;
import com.ecommerceapp.notificationservice.notification.Notification;
import com.ecommerceapp.notificationservice.notification.NotificationRepository;
import com.ecommerceapp.notificationservice.notification.dto.OrderConfirmation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;

    // Kafka Consumer
    @KafkaListener(topics = "order-topic", groupId = "orderGroup")
    public void consumeOrderConfirmation(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("=====>consuming the message from order: {}", orderConfirmation);
        Notification notification = Notification.builder()
                .notificationDate(LocalDateTime.now())
                .orderId(orderConfirmation.id())
                .build();

        repository.save(notification);

        //send email
        emailService.sendOrderConfirmationMail(orderConfirmation.userEmail(), orderConfirmation);

    }

    @KafkaListener(topics = "auth-topic", groupId = "authGroup")
    public void consumeVerificationCodes(AuthConfirmation authConfirmation) throws MessagingException {
        log.info("---------------->Consuming auth: {}", authConfirmation);

        emailService.sendVerificationCode(authConfirmation.email(), authConfirmation.code());
    }

}
