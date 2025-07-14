package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter;


import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.infrastructure.output.adapter.UserJpaAdapter;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository.IUserRepository;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.plazadecomidas.authservice.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @Mock
    private IUserRepository userRepository;
    @Mock
    private IUserEntityMapper userEntityMapper;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    private User testUser;
    private UserEntity testUserEntity;
    Role role;
    RoleEntity roleEntity;

    @BeforeEach
    void setUp() {
        role = new Role(1L, "PROPIETARIO", "Descripción del propietario");
        roleEntity = new RoleEntity(1L, "PROPIETARIO", "Descripción del propietario");

        testUser = User.builder()
                .id(1L)
                .name("Test")
                .lastName("User")
                .identityDocument("123456789")
                .phoneNumber("+573001234567")
                .email("test@example.com")
                .password("encodedPassword")
                .birthDate(LocalDate.of(1990, 1, 1))
                .roleName("PROPIETARIO")
                .build();

        testUserEntity = new UserEntity(
                1L,
                "Test",
                "User",
                "123456789",
                "+573001234567",
                "test@example.com",
                "encodedPassword",
                LocalDate.of(1990, 1, 1),
                roleEntity
        );
    }

    @Test
    @DisplayName("Debería guardar un usuario exitosamente")
    void saveUser_ShouldSaveUserSuccessfully() {
        when(userEntityMapper.toUserEntity(any(User.class))).thenReturn(testUserEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUserEntity);
        when(userEntityMapper.toUser(any(UserEntity.class))).thenReturn(testUser);

        User savedUser = userJpaAdapter.save(testUser);

        assertNotNull(savedUser);
        assertEquals(testUser.getId(), savedUser.getId());
        verify(userEntityMapper, times(1)).toUserEntity(testUser);
        verify(userRepository, times(1)).save(testUserEntity);
        verify(userEntityMapper, times(1)).toUser(testUserEntity);
    }

    @Test
    @DisplayName("Debería encontrar un usuario por ID")
    void findById_ShouldReturnUserWhenFound() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUserEntity));
        when(userEntityMapper.toUser(testUserEntity)).thenReturn(testUser);

        Optional<User> foundUser = userJpaAdapter.findById(testUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getId(), foundUser.get().getId());
        verify(userRepository, times(1)).findById(testUser.getId());
        verify(userEntityMapper, times(1)).toUser(testUserEntity);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty si el usuario no es encontrado por ID")
    void findById_ShouldReturnEmptyOptionalWhenNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<User> foundUser = userJpaAdapter.findById(999L);

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById(999L);
        verifyNoInteractions(userEntityMapper);
    }

    @Test
    @DisplayName("Debería encontrar un usuario por email")
    void findByEmail_ShouldReturnUserWhenFound() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUserEntity));
        when(userEntityMapper.toUser(testUserEntity)).thenReturn(testUser);

        Optional<User> foundUser = userJpaAdapter.findByEmail(testUser.getEmail());

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(testUser.getEmail());
        verify(userEntityMapper, times(1)).toUser(testUserEntity);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty si el usuario no es encontrado por email")
    void findByEmail_ShouldReturnEmptyOptionalWhenNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Optional<User> foundUser = userJpaAdapter.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
        verifyNoInteractions(userEntityMapper);
    }

    @Test
    @DisplayName("Debería encontrar un usuario por número de teléfono")
    void findByPhoneNumber_ShouldReturnUserWhenFound() {
        when(userRepository.findByPhoneNumber(testUser.getPhoneNumber())).thenReturn(Optional.of(testUserEntity));
        when(userEntityMapper.toUser(testUserEntity)).thenReturn(testUser);

        Optional<User> foundUser = userJpaAdapter.findByPhone(testUser.getPhoneNumber());

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getPhoneNumber(), foundUser.get().getPhoneNumber());
        verify(userRepository, times(1)).findByPhoneNumber(testUser.getPhoneNumber());
        verify(userEntityMapper, times(1)).toUser(testUserEntity);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty si el usuario no es encontrado por número de teléfono")
    void findByPhoneNumber_ShouldReturnEmptyOptionalWhenNotFound() {
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());

        Optional<User> foundUser = userJpaAdapter.findByPhone("+571234567890");

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByPhoneNumber("+571234567890");
        verifyNoInteractions(userEntityMapper);
    }

    @Test
    @DisplayName("Debería encontrar un usuario por documento de identidad")
    void findByIdentityDocument_ShouldReturnUserWhenFound() {
        when(userRepository.findByIdentityDocument(testUser.getIdentityDocument())).thenReturn(Optional.of(testUserEntity));
        when(userEntityMapper.toUser(testUserEntity)).thenReturn(testUser);

        Optional<User> foundUser = userJpaAdapter.findByIdentification(testUser.getIdentityDocument());

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getIdentityDocument(), foundUser.get().getIdentityDocument());
        verify(userRepository, times(1)).findByIdentityDocument(testUser.getIdentityDocument());
        verify(userEntityMapper, times(1)).toUser(testUserEntity);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty si el usuario no es encontrado por documento de identidad")
    void findByIdentityDocument_ShouldReturnEmptyOptionalWhenNotFound() {
        when(userRepository.findByIdentityDocument(anyString())).thenReturn(Optional.empty());

        Optional<User> foundUser = userJpaAdapter.findByIdentification("999999999");

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByIdentityDocument("999999999");
        verifyNoInteractions(userEntityMapper);
    }
}
