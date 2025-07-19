package com.ecommerceapp.gatewayservice.filter;

import org.springframework.stereotype.Component;
import org.springframework.http.server.reactive.ServerHttpRequest;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openEndPoints = List.of(
            "/api/v1/auth/**",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecure =
            serverHttpRequest -> openEndPoints.stream().noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
}
