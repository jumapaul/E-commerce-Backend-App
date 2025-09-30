package com.ecommerceapp.orderservice.cart_client;

import com.ecommerceapp.orderservice.feign.FeignClientConfiguration;
import com.ecommerceapp.orderservice.order.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
            @PathVariable(name = "userId") Long userId
    );

    @PostMapping("/{userId}")
    ApiResponse<Cart> addToCart(
            @RequestBody CartItem request,
            @PathVariable Long userId);

    @DeleteMapping("/clear/{userId}")
    ApiResponse<String> clearCart(
            @PathVariable(name = "userId") Long userId
    );
}
