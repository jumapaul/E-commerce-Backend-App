package com.ecommerceapp.gatewayservice.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;


@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> errorResponse = super.getErrorAttributes(request, options);

        HttpStatus status = HttpStatus.valueOf((Integer) errorResponse.get("status"));

        switch (status) {
            case UNAUTHORIZED:
                errorResponse.put("message", "Invalid token");
                break;
            case BAD_REQUEST:
                errorResponse.put("Message", "Bad request");
                break;
            default:
                errorResponse.put("message", "Something went wrong");
        }

        return errorResponse;
    }
}
