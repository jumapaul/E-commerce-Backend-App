package com.ecommerceapp.authenticationservice.user.services;

import com.ecommerceapp.authenticationservice.user.dtos.LoginRequest;
import com.ecommerceapp.authenticationservice.user.dtos.RegisterRequest;
import com.ecommerceapp.authenticationservice.user.dtos.ResendVerificationCodeRequest;
import com.ecommerceapp.authenticationservice.user.dtos.VerifyRequest;
import com.ecommerceapp.authenticationservice.user.response.ApiResponse;
import com.ecommerceapp.authenticationservice.user.response.LoginResponse;
import com.ecommerceapp.authenticationservice.user.response.RegisterResponse;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    ApiResponse<RegisterResponse> register(RegisterRequest request);
    ApiResponse<LoginResponse> login(LoginRequest request);
    ApiResponse<String> verifyUser(VerifyRequest request);
    ApiResponse<String> resendVerificationCode(ResendVerificationCodeRequest email) throws MessagingException;
}
