package com.ecommerceapp.notificationservice.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class ConsumerConfig {

    private String bootStrapServers = "localhost:9092";
}
