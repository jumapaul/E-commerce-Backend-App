package com.ecommerceapp.notificationservice.notification;

import com.ecommerceapp.notificationservice.notification.dto.OrderConfirmation;
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

    @KafkaListener(topics = "order-topic", groupId = "orderGroup")
    public void consumeOrderConfirmation(OrderConfirmation orderConfirmation) {
        log.info("=====>consuming the message from order: {}", orderConfirmation);
        //save order

        Notification notification = Notification.builder()
                .notificationDate(LocalDateTime.now())
                .orderId(orderConfirmation.id())
                .build();

        repository.save(notification);

    }

}
