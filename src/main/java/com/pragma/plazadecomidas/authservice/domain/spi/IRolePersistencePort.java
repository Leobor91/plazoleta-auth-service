package com.pragma.plazadecomidas.authservice.domain.spi;

import com.pragma.plazadecomidas.authservice.domain.model.Role;

import java.util.Optional;

public interface IRolePersistencePort {

    Optional<Role> findByName(String name);

    Optional<Role> findById(Long id);
}
