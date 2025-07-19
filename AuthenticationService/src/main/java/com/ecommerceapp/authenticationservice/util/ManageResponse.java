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
}
