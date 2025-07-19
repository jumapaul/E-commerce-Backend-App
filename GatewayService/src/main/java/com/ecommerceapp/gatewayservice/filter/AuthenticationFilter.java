package com.ecommerceapp.gatewayservice.filter;

import com.ecommerceapp.gatewayservice.exceptions.BadRequestException;
import com.ecommerceapp.gatewayservice.exceptions.InvalidTokenException;
import com.ecommerceapp.gatewayservice.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtService service;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (validator.isSecure.test(request)) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new BadRequestException(HttpStatus.BAD_REQUEST);
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    service.validToken(authHeader);
                } catch (Exception e) {
                    throw new InvalidTokenException(HttpStatus.UNAUTHORIZED);
                }
            }

            return chain.filter(exchange);
        }
        )
        );
    }

    public static class Config {

    }

}
