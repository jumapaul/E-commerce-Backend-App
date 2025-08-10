package com.ecommerceapp.authenticationservice.user.service_impl;

import com.ecommerceapp.authenticationservice.config.JwtService;
import com.ecommerceapp.authenticationservice.kafka.AuthConfirmation;
import com.ecommerceapp.authenticationservice.kafka.producer.AuthProducer;
import com.ecommerceapp.authenticationservice.user.Role;
import com.ecommerceapp.authenticationservice.user.dtos.*;
import com.ecommerceapp.authenticationservice.user.entities.UserEntity;
import com.ecommerceapp.authenticationservice.exception.ResourceNotFoundException;
import com.ecommerceapp.authenticationservice.user.repository.UserRepository;
import com.ecommerceapp.authenticationservice.user.response.ApiResponse;
import com.ecommerceapp.authenticationservice.user.response.LoginResponse;
import com.ecommerceapp.authenticationservice.user.response.RegisterResponse;
import com.ecommerceapp.authenticationservice.user.services.AuthenticationService;
import com.ecommerceapp.authenticationservice.exception.ConflictException;
import com.ecommerceapp.authenticationservice.exception.UnAuthorizedException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static com.ecommerceapp.authenticationservice.util.ManageResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthProducer authProducer;

    @Override
    public ApiResponse<RegisterResponse> register(@Valid RegisterRequest request) throws MessagingException {
        var userExists = repository.findByEmail(request.email()).isPresent();

        if (userExists) throw new ConflictException("User already exists");

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

        AuthConfirmation authConfirmation = new AuthConfirmation(
                user.getEmail(), user.getVerificationCode()
        );
        //send to producer
        authProducer.sendVerificationCode(authConfirmation);

        RegisterResponse response = new RegisterResponse(
                user.getUsername(), user.getEmail(), user.getRole().name(), user.getVerificationCode()
        );

        return onSuccess("Registration successful", response);
    }

    @Override
    public ApiResponse<String> verifyUser(VerifyRequest request) {
        var user = repository.findByEmail(request.email()).orElseThrow(() ->
                new ResourceNotFoundException("User with email " + request.email() + " not found")
        );

        if (user.isEnabled()) throw new ConflictException("Account already verified");

        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now()))
            throw new BadCredentialsException("Verification code has expired");

        if (user.getVerificationCode().equals(request.verificationCode())) {
            user.setEnabled(true);
            user.setVerificationCode(null);
            user.setVerificationCodeExpiresAt(null);

            repository.save(user);

            return onSuccess("User successfully verified", null);
        } else {
            throw new BadCredentialsException("Invalid verification code");
        }
    }

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        var user = repository.findByEmail(request.email()).orElseThrow(() ->
                new ResourceNotFoundException("User not found")
        );

        if (!user.isEnabled()) throw new UnAuthorizedException("Email not verified");

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
    }

    @Override
    public ApiResponse<String> resendVerificationCode(ResendVerificationCodeRequest request) throws MessagingException {
        var user = repository.findByEmail(request.email()).orElseThrow(() ->
                new ResourceNotFoundException("User with email " + request.email() + " not found")
        );

        if (user.isEnabled()) throw new BadCredentialsException("User already verified");

        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(30));

        repository.save(user);

        AuthConfirmation confirmation = new AuthConfirmation(
                user.getEmail(), user.getVerificationCode()
        );
        //send email
        authProducer.sendVerificationCode(confirmation);
        return onSuccess("Verification code sent successfully", user.getVerificationCode());
    }

    @Override
    public ApiResponse<String> resetPassword(ResetPasswordRequest request) {
        var user = repository.findByEmail(request.email()).orElseThrow(() ->
                new ResourceNotFoundException("User with " + request.email() + " not found")
        );

        var isPasswordValid = passwordEncoder.matches(request.initialPassword(), user.getPassword());

        if (!isPasswordValid) {
            throw new BadCredentialsException("Incorrect password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        repository.save(user);
        return onSuccess("Password reset successful", null);
    }

    @Override
    public ApiResponse<UserResponse> getUserById(Long userId) {
        UserEntity user = repository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + userId + " not found")
        );

        UserResponse response = new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User retrieved successfully",
                response
        );
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
