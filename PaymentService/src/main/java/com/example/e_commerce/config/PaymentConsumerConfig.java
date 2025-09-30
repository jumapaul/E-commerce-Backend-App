package com.example.e_commerce.config;


import com.example.e_commerce.dtos.PaymentsEntity;
import com.example.e_commerce.event.OrderEvent;
import com.example.e_commerce.event.OrderStatus;
import com.example.e_commerce.event.PaymentEvent;
import com.example.e_commerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PaymentConsumerConfig {

    private final PaymentService paymentService;

    //We do not emit the event immediately because the callback isn't returned
    @Bean
    public Function<Flux<OrderEvent>, Flux<Void>> paymentProcessor() {
        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<Void> processPayment(OrderEvent orderEvent) {
        try {
            if (OrderStatus.CREATED.equals(orderEvent.getOrderStatus())) {
                return Mono.fromCallable(()->this.paymentService.initiateStkPush(orderEvent.getInternalStkPushRequest()))
                        .subscribeOn(Schedulers.boundedElastic())
                        .then();
            } else {
                return Mono.empty();
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
