package com.ecommerceapp.shoppingcartservice.shoppingCart.productClient;

import com.ecommerceapp.shoppingcartservice.shoppingCart.exception.AccessDeniedException;
import com.ecommerceapp.shoppingcartservice.shoppingCart.exception.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomFeignDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(CustomFeignDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        String responseBody = extractResponseBody(response);

        logger.error("Feign client error. Method: {}, status: {}, Body: {}", methodKey, status, responseBody);

        return switch (status) {
            case BAD_REQUEST -> new IllegalArgumentException("Invalid request: " + responseBody);
            case UNAUTHORIZED -> new SecurityException("Unauthorized access");
            case FORBIDDEN -> new AccessDeniedException("Access forbidden");
            case NOT_FOUND -> new ResourceNotFoundException("Resource not found");
            case INTERNAL_SERVER_ERROR -> new RuntimeException("Internal server error");
//            case SERVICE_UNAVAILABLE:
//                return new RetryableException(response.status(), "Service unavailable", null, null, null);
            default -> new Exception("Unexpected error: " + responseBody);
        };
    }

    private String extractResponseBody(Response response) {
        if (response.body() == null) {
            return "No response body";
        }

        try {
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Failed to read response body", e);
            return "Error reading response body";
        }
    }
}
