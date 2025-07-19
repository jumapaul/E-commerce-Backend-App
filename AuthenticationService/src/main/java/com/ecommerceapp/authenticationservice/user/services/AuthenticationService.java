package com.ecommerceapp.authenticationservice.user.services;

import com.ecommerceapp.authenticationservice.user.dtos.*;
import com.ecommerceapp.authenticationservice.user.response.ApiResponse;
import com.ecommerceapp.authenticationservice.user.response.LoginResponse;
import com.ecommerceapp.authenticationservice.user.response.RegisterResponse;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    ApiResponse<RegisterResponse> register(RegisterRequest request) throws MessagingException;
    ApiResponse<LoginResponse> login(LoginRequest request);
    ApiResponse<String> verifyUser(VerifyRequest request);
    ApiResponse<String> resendVerificationCode(ResendVerificationCodeRequest email) throws MessagingException;

    ApiResponse<String> resetPassword(ResetPasswordRequest request);
}
