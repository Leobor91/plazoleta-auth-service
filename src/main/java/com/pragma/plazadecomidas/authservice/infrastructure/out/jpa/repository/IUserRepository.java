package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository;

import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByDniNumber(String identityDocument);

    boolean existsByPhone(String phoneNumber);

    Optional<UserEntity> findById(Long id);
}
