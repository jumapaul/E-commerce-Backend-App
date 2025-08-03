package com.ecommerceapp.shoppingcartservice.shoppingCart.productClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(
        name = "product-client",
        configuration = FeignClientConfiguration.class,
        url = "${application.config.product-url}"
)
public interface ProductClient {

    @GetMapping("/{productId}")
    Optional<ProductResponse> getProductById(
            @PathVariable(name = "productId") String productId,
            @RequestHeader("Authorization") String authHeader);
}
