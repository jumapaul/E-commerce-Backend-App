package com.ecommerceapp.authenticationservice.user.dtos;

public record ResetPasswordRequest(
        String email,
        String initialPassword,
        String newPassword
) {
}
