package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository;

import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);

    boolean existsByName(String name);

    boolean existsById(Long id);
}
