package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter;

import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository.IRoleRepository;
import com.pragma.plazadecomidas.authservice.infrastructure.output.adapter.RoleJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleJpaAdapterTest {

    @Mock
    private IRoleRepository roleRepository;
    @Mock
    private IRoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RoleJpaAdapter roleJpaAdapter;

    private Role testRole;
    private RoleEntity testRoleEntity;

    @BeforeEach
    void setUp() {
        testRole = new Role(1L, "PROPIETARIO", "Descripción del propietario");
        testRoleEntity = new RoleEntity(1L, "PROPIETARIO", "Descripción del propietario");
    }

    @Test
    @DisplayName("Debería encontrar un rol por nombre")
    void findByName_ShouldReturnRoleWhenFound() {
        when(roleRepository.findByName(testRole.getName())).thenReturn(Optional.of(testRoleEntity));
        when(roleEntityMapper.toRole(testRoleEntity)).thenReturn(testRole);

        Optional<Role> foundRole = roleJpaAdapter.findByName(testRole.getName());

        assertTrue(foundRole.isPresent());
        assertEquals(testRole.getName(), foundRole.get().getName());
        verify(roleRepository, times(1)).findByName(testRole.getName());
        verify(roleEntityMapper, times(1)).toRole(testRoleEntity);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty si el rol no es encontrado por nombre")
    void findByName_ShouldReturnEmptyOptionalWhenNotFound() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

        Optional<Role> foundRole = roleJpaAdapter.findByName("ROL_INEXISTENTE");

        assertFalse(foundRole.isPresent());
        verify(roleRepository, times(1)).findByName("ROL_INEXISTENTE");
        verifyNoInteractions(roleEntityMapper);
    }
}
