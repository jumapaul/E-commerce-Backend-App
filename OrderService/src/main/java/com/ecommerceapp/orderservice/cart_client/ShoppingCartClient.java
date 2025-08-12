package com.ecommerceapp.orderservice.cart_client;

import com.ecommerceapp.orderservice.feign.FeignClientConfiguration;
import com.ecommerceapp.orderservice.order.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Optional;

@FeignClient(
        name = "cart",
        url = "${application.config.shopping-cart-url}",
        configuration = FeignClientConfiguration.class
)
public interface ShoppingCartClient {
    //External requests

    @GetMapping("/{userId}")
    Optional<ApiResponse<ShoppingCartResponse>> getCart(
            @PathVariable(name = "userId") Long userId,
            @RequestHeader("Authorization") String authHeader
    );

    @DeleteMapping("delete/{userId}")
    ApiResponse<String> deleteCart(
            @PathVariable(name = "userId") Long userId,
            @RequestHeader("Authorization") String authHeader
    );
}
