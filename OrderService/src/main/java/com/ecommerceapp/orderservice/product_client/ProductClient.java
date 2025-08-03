package com.ecommerceapp.orderservice.product_client;

import com.ecommerceapp.orderservice.feign.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "product-client",
        url = "${application.config.product-url}",
        configuration = FeignClientConfiguration.class
)
public interface ProductClient {

    @GetMapping("/{productId}")
    ProductResponse getProductById(
            @PathVariable(name = "productId") String productId,
            @RequestHeader("Authorization") String authHeader
    );

    @PutMapping
    void updateProduct(
            @RequestBody ProductRequest request,
            @RequestHeader("Authorization") String authHeader
    );
}
