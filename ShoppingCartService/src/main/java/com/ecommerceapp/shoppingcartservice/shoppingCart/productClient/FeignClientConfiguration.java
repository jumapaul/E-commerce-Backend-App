package com.ecommerceapp.shoppingcartservice.shoppingCart.productClient;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder customFeignDecoder() {
        return new CustomFeignDecoder();
    }
}
