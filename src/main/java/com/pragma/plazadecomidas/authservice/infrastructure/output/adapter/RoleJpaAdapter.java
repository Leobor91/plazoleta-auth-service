package com.pragma.plazadecomidas.authservice.infrastructure.output.adapter;

import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.spi.IRolePersistencePort;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleJpaAdapter implements IRolePersistencePort {

    private  final IRoleRepository roleRepository;
    private  final IRoleEntityMapper roleEntityMapper;
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name)
                .map(roleEntityMapper::toRole);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id)
                .map(roleEntityMapper::toRole);
    }

}
