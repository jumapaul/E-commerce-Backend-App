package com.ecommerceapp.authenticationservice.kafka.consumer;

import com.ecommerceapp.authenticationservice.kafka.AuthConfirmation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ConsumerConfigurations {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootStrapServers;

    @Value("${spring.kafka.security.protocol:PLAINTEXT}")
    private String securityProtocol;

    @Value("${spring.kafka.sasl.mechanism:PLAIN}")
    private String saslMechanism;

    @Value("${spring.kafka.sasl.jaas.config:}")
    private String jaasConfig;

    @Bean
    public ConsumerFactory<String, AuthConfirmation> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "java-group-1"); //the group is java group-1
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        if (!"PLAINTEXT".equals(securityProtocol)) {
            props.put("security.protocol", securityProtocol);
            props.put("sasl.mechanism", saslMechanism);
            props.put("sasl.jaas.config", jaasConfig);
        }

        props.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, true);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, AuthConfirmation.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AuthConfirmation> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AuthConfirmation> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
