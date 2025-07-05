package com.ecommerceapp.authenticationservice.user.service_impl;

import com.ecommerceapp.authenticationservice.config.JwtService;
import com.ecommerceapp.authenticationservice.config.email.EmailService;
import com.ecommerceapp.authenticationservice.user.Role;
import com.ecommerceapp.authenticationservice.user.dtos.ResendVerificationCodeRequest;
import com.ecommerceapp.authenticationservice.user.entities.UserEntity;
import com.ecommerceapp.authenticationservice.user.repository.UserRepository;
import com.ecommerceapp.authenticationservice.user.dtos.LoginRequest;
import com.ecommerceapp.authenticationservice.user.dtos.RegisterRequest;
import com.ecommerceapp.authenticationservice.user.dtos.VerifyRequest;
import com.ecommerceapp.authenticationservice.user.response.ApiResponse;
import com.ecommerceapp.authenticationservice.user.response.LoginResponse;
import com.ecommerceapp.authenticationservice.user.response.RegisterResponse;
import com.ecommerceapp.authenticationservice.user.services.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static com.ecommerceapp.authenticationservice.util.ManageResponse.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public ApiResponse<RegisterResponse> register(RegisterRequest request) {
        try {
            var userExists = repository.findByEmail(request.email()).isPresent();

            if (userExists) return onConflict("User already exists");

            var user = UserEntity.builder()
                    .username(request.username())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .role(Role.USER)
                    .isEnabled(false)
                    .verificationCode(generateVerificationCode())
                    .verificationCodeExpiresAt(LocalDateTime.now().plusMinutes(30))
                    .build();

            repository.save(user);

            //send email
            emailService.sendEmailVerificationCode(user.getEmail(), user.getVerificationCode());

            RegisterResponse response = new RegisterResponse(
                    user.getUsername(), user.getEmail(), user.getRole().name(), user.getVerificationCode()
            );

            return onSuccess("Registration successful", response);
        } catch (Exception e) {
            return onUnAuthorized(e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> verifyUser(VerifyRequest request) {
        var user = repository.findByEmail(request.email()).orElseThrow(() ->
                new EntityNotFoundException("User with email " + request.email() + " not found")
        );

        if (user.isEnabled()) return onBadRequest("Account already verified");

        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now()))
            return onBadRequest("Verification code has expired");

        if (user.getVerificationCode().equals(request.verificationCode())) {
            user.setEnabled(true);
            user.setVerificationCode(null);
            user.setVerificationCodeExpiresAt(null);

            repository.save(user);

            return onSuccess("User successfully verified", null);
        } else {
            return onBadRequest("Invalid verification code");
        }
    }

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        try {
            var user = repository.findByEmail(request.email()).orElseThrow(() ->
                    new EntityNotFoundException("User not found")
            );

            if (!user.isEnabled()) return onUnAuthorized("Email not verified");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            String token = jwtService.generateToken(user);

            var loginResponse = new LoginResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    token
            );
            return onSuccess("Login successful", loginResponse);
        } catch (Exception e) {
            return onUnAuthorized(e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> resendVerificationCode(ResendVerificationCodeRequest request) throws MessagingException {
        var user = repository.findByEmail(request.email()).orElseThrow(() ->
                new EntityNotFoundException("User with email " + request.email() + " not found")
        );

        if (user.isEnabled()) return onBadRequest("User already verified");

        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(30));

        repository.save(user);

        //send email
        emailService.sendEmailVerificationCode(user.getEmail(), user.getVerificationCode());
        return onSuccess("Verification code sent successfully", user.getVerificationCode());
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
