package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter;

import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository.IRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleJpaAdapterTest {

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private IRoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RoleJpaAdapter roleJpaAdapter;

    private Role domainRole;
    private RoleEntity roleEntity;

    @BeforeEach
    void setUp() {
        domainRole = Role.builder()
                .id(1L)
                .name("ADMIN")
                .description("Administrator Role")
                .build();

        roleEntity = RoleEntity.builder()
                .id(1L)
                .name("ADMIN")
                .description("Administrator Role")
                .build();
    }

    // --- findByName tests ---

    @Test
    @DisplayName("findByName: Should return Optional of Role if found by name")
    void findByName_RoleFound_ReturnsOptionalOfRole() {
        // GIVEN
        String roleName = "ADMIN";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(roleEntity));
        when(roleEntityMapper.toRole(roleEntity)).thenReturn(domainRole);

        // WHEN
        Optional<Role> result = roleJpaAdapter.findByName(roleName);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(domainRole.getId(), result.get().getId());
        assertEquals(domainRole.getName(), result.get().getName());

        verify(roleRepository).findByName(roleName);
        verify(roleEntityMapper).toRole(roleEntity);
    }

    @Test
    @DisplayName("findByName: Should return Optional empty if role not found by name")
    void findByName_RoleNotFound_ReturnsOptionalEmpty() {
        // GIVEN
        String roleName = "NON_EXISTENT_ROLE";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        // WHEN
        Optional<Role> result = roleJpaAdapter.findByName(roleName);

        // THEN
        assertFalse(result.isPresent());

        verify(roleRepository).findByName(roleName);
        verify(roleEntityMapper, never()).toRole(any(RoleEntity.class)); // Mapper should not be called
    }

    @Test
    @DisplayName("findByName: Should return Optional empty if name is null")
    void findByName_NameIsNull_ReturnsOptionalEmpty() {
        // WHEN
        Optional<Role> result = roleJpaAdapter.findByName(null);

        // THEN
        assertFalse(result.isPresent());

        verify(roleRepository, never()).findByName(anyString()); // Repository should not be called
        verify(roleEntityMapper, never()).toRole(any(RoleEntity.class)); // Mapper should not be called
    }

    // --- findById tests ---

    @Test
    @DisplayName("findById: Should return Optional of Role if found by ID")
    void findById_RoleFound_ReturnsOptionalOfRole() {
        // GIVEN
        Long roleId = 1L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(roleEntity));
        when(roleEntityMapper.toRole(roleEntity)).thenReturn(domainRole);

        // WHEN
        Optional<Role> result = roleJpaAdapter.findById(roleId);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(domainRole.getId(), result.get().getId());
        assertEquals(domainRole.getName(), result.get().getName());

        verify(roleRepository).findById(roleId);
        verify(roleEntityMapper).toRole(roleEntity);
    }

    @Test
    @DisplayName("findById: Should return Optional empty if role not found by ID")
    void findById_RoleNotFound_ReturnsOptionalEmpty() {
        // GIVEN
        Long roleId = 99L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // WHEN
        Optional<Role> result = roleJpaAdapter.findById(roleId);

        // THEN
        assertFalse(result.isPresent());

        verify(roleRepository).findById(roleId);
        verify(roleEntityMapper, never()).toRole(any(RoleEntity.class)); // Mapper should not be called
    }

    @Test
    @DisplayName("findById: Should return Optional empty if ID is null")
    void findById_IdIsNull_ReturnsOptionalEmpty() {
        // WHEN
        Optional<Role> result = roleJpaAdapter.findById(null);

        // THEN
        assertFalse(result.isPresent());

        verify(roleRepository, never()).findById(anyLong()); // Repository should not be called
        verify(roleEntityMapper, never()).toRole(any(RoleEntity.class)); // Mapper should not be called
    }
}
