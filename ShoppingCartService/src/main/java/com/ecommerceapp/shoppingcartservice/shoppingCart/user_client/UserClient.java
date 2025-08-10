package com.ecommerceapp.shoppingcartservice.shoppingCart.user_client;

import com.ecommerceapp.shoppingcartservice.shoppingCart.ApiResponse;
import com.ecommerceapp.shoppingcartservice.shoppingCart.productClient.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(
        name = "auth-service",
        url = "${application.config.user-url}",
        configuration = FeignClientConfiguration.class
)
public interface UserClient {

    @GetMapping("/{userId}")
    Optional<ApiResponse<UserResponse>> getUserById(
            @PathVariable(name = "userId") Long userId,
            @RequestHeader("Authorization") String authHeader
    );
}
