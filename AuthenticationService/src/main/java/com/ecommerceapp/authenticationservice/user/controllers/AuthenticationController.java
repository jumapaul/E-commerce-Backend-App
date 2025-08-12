package com.ecommerceapp.authenticationservice.user.controllers;


import com.ecommerceapp.authenticationservice.user.dtos.*;
import com.ecommerceapp.authenticationservice.user.entities.UserEntity;
import com.ecommerceapp.authenticationservice.user.response.ApiResponse;
import com.ecommerceapp.authenticationservice.user.response.LoginResponse;
import com.ecommerceapp.authenticationservice.user.response.RegisterResponse;
import com.ecommerceapp.authenticationservice.user.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerUser(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/verifyUser")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestBody VerifyRequest request) {
        return ResponseEntity.ok(authService.verifyUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/resendVerificationCode")
    public ResponseEntity<ApiResponse<String>> resendVerificationCode(@RequestBody ResendVerificationCodeRequest request) {
        return ResponseEntity.ok(authService.resendVerificationCode(request));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }
}
