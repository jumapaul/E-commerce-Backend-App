package com.ecommerceapp.orderservice.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder customFeignDecoder(){
        return new CustomFeignDecoder();
    }
}
