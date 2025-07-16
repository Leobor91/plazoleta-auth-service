package com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository;

import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdentityDocument(String identification);

    Optional<UserEntity> findByPhoneNumber(String phone);

}
