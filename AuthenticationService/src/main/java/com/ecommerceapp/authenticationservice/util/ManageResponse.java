package com.ecommerceapp.authenticationservice.util;

import com.ecommerceapp.authenticationservice.user.response.ApiResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class ManageResponse {

    public static <T> ApiResponse<T> onSuccess(String desc, T data) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                desc,
                data
        );
    }

    public static <T> ApiResponse<T> onUnAuthorized(String desc) {
        return new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                desc,
                null
        );
    }

    public static <T> ApiResponse<T> onBadRequest(String desc) {
        return new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                desc,
                null
        );
    }

    public static <T> ApiResponse<T> onConflict(String desc) {
        return new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                desc,
                null
        );
    }
}
