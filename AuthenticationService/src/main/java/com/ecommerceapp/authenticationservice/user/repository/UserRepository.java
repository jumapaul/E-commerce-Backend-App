package com.ecommerceapp.authenticationservice.user.repository;

import com.ecommerceapp.authenticationservice.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
