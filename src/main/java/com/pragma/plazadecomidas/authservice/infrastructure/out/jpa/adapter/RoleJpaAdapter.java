package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter;

import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.spi.IRolePersistencePort;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public Optional<Role> findByName(String name) {
        return Optional.ofNullable(name)
                .flatMap(roleRepository::findByName)
                .map(roleEntityMapper::toRole);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(roleRepository::findById)
                .map(roleEntityMapper::toRole);
    }
}
