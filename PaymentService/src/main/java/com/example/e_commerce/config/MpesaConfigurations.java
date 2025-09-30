package com.example.e_commerce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "mpesa.daraja")
public class MpesaConfigurations {
    private String consumerKey;
    private String consumerSecret;
    private String grantType;
    private String callbackUrl;
    private String shortCode;
    private String passKey;
}
