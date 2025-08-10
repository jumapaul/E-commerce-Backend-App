package com.ecommerceapp.authenticationservice.user.controllers;

import com.ecommerceapp.authenticationservice.user.dtos.UserResponse;
import com.ecommerceapp.authenticationservice.user.response.ApiResponse;
import com.ecommerceapp.authenticationservice.user.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable(name = "userId") Long userId) {

        return ResponseEntity.ok(authService.getUserById(userId));
    }
}
