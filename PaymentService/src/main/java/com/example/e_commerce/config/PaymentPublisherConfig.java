package com.example.e_commerce.config;

import com.example.e_commerce.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
@Slf4j
public class PaymentPublisherConfig {

    @Bean
    public Sinks.Many<PaymentEvent> paymentSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<PaymentEvent>> paymentSupplier(Sinks.Many<PaymentEvent> paymentSink) {
        return () -> paymentSink.asFlux()
                .doOnNext(event -> log.info("Publishing kafka event ===> {}", event));
    }
}
