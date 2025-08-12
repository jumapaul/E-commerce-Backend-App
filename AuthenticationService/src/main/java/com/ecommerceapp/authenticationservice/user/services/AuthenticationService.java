package com.ecommerceapp.authenticationservice.user.services;

import com.ecommerceapp.authenticationservice.user.dtos.*;
import com.ecommerceapp.authenticationservice.user.response.ApiResponse;
import com.ecommerceapp.authenticationservice.user.response.LoginResponse;
import com.ecommerceapp.authenticationservice.user.response.RegisterResponse;

public interface AuthenticationService {

    ApiResponse<RegisterResponse> register(RegisterRequest request);
    ApiResponse<LoginResponse> login(LoginRequest request);
    ApiResponse<String> verifyUser(VerifyRequest request);
    ApiResponse<String> resendVerificationCode(ResendVerificationCodeRequest email);

    ApiResponse<String> resetPassword(ResetPasswordRequest request);

    ApiResponse<UserResponse> getUserById(Long userId);
}
