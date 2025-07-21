package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter;

import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository.IUserRepository;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    private User userDomain;
    private UserEntity userEntity;
    private Role ownerRole;
    private RoleEntity ownerRoleEntity;

    @BeforeEach
    void setUp() {
        ownerRole = Role.builder()
                .id(1L)
                .name("OWNER")
                .description("Owner Role")
                .build();

        ownerRoleEntity = RoleEntity.builder()
                .id(1L)
                .name("OWNER")
                .description("Owner Role")
                .build();

        userDomain = User.builder()
                .id(null)
                .name("Test")
                .lastName("User")
                .dniNumber("1234567890")
                .phone("+573001112233")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("test.user@example.com")
                .password("encodedPass")
                .role(ownerRole)
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .name("Test")
                .lastName("User")
                .dniNumber("1234567890")
                .phone("+573001112233")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("test.user@example.com")
                .password("encodedPass")
                .role(ownerRoleEntity)
                .build();
    }

    @Test
    @DisplayName("saveUser: Should map, save, and return mapped user")
    void saveUser_Success() {
        // GIVEN
        User userToSave = userDomain.toBuilder().build(); // Clone to ensure no modification
        UserEntity entityToSave = userEntity.toBuilder().id(null).build(); // Entity without ID as it's being saved
        UserEntity savedEntity = userEntity.toBuilder().id(1L).build(); // Entity with generated ID
        User expectedDomainUser = userDomain.toBuilder().id(1L).build();

        when(userEntityMapper.toUserEntity(userToSave)).thenReturn(entityToSave);
        when(userRepository.save(entityToSave)).thenReturn(savedEntity);
        when(userEntityMapper.toUser(savedEntity)).thenReturn(expectedDomainUser);

        // WHEN
        User result = userJpaAdapter.saveUser(userToSave);

        // THEN
        assertNotNull(result);
        assertEquals(expectedDomainUser.getId(), result.getId());
        assertEquals(expectedDomainUser.getEmail(), result.getEmail());
        assertEquals(expectedDomainUser.getRole().getName(), result.getRole().getName());

        verify(userEntityMapper).toUserEntity(userToSave);
        verify(userRepository).save(entityToSave);
        verify(userEntityMapper).toUser(savedEntity);
    }

    @Test
    @DisplayName("existsByEmail: Should return true if user exists by email")
    void existsByEmail_UserExists_ReturnsTrue() {
        // GIVEN
        String email = "test.user@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // WHEN
        boolean exists = userJpaAdapter.existsByEmail(email);

        // THEN
        assertTrue(exists);
        verify(userRepository).existsByEmail(email);
    }

    @Test
    @DisplayName("existsByEmail: Should return false if user does not exist by email")
    void existsByEmail_UserDoesNotExist_ReturnsFalse() {
        // GIVEN
        String email = "non.existent@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        // WHEN
        boolean exists = userJpaAdapter.existsByEmail(email);

        // THEN
        assertFalse(exists);
        verify(userRepository).existsByEmail(email);
    }

    @Test
    @DisplayName("existsByDniNumber: Should return true if user exists by DNI number")
    void existsByDniNumber_UserExists_ReturnsTrue() {
        // GIVEN
        String dni = "1234567890";
        when(userRepository.existsByDniNumber(dni)).thenReturn(true);

        // WHEN
        boolean exists = userJpaAdapter.existsByDniNumber(dni);

        // THEN
        assertTrue(exists);
        verify(userRepository).existsByDniNumber(dni);
    }

    @Test
    @DisplayName("existsByDniNumber: Should return false if user does not exist by DNI number")
    void existsByDniNumber_UserDoesNotExist_ReturnsFalse() {
        // GIVEN
        String dni = "0000000000";
        when(userRepository.existsByDniNumber(dni)).thenReturn(false);

        // WHEN
        boolean exists = userJpaAdapter.existsByDniNumber(dni);

        // THEN
        assertFalse(exists);
        verify(userRepository).existsByDniNumber(dni);
    }

    @Test
    @DisplayName("existsByPhoneNumber: Should return true if user exists by phone number")
    void existsByPhoneNumber_UserExists_ReturnsTrue() {
        // GIVEN
        String phone = "+573001112233";
        when(userRepository.existsByPhone(phone)).thenReturn(true);

        // WHEN
        boolean exists = userJpaAdapter.existsByPhoneNumber(phone);

        // THEN
        assertTrue(exists);
        verify(userRepository).existsByPhone(phone);
    }

    @Test
    @DisplayName("existsByPhoneNumber: Should return false if user does not exist by phone number")
    void existsByPhoneNumber_UserDoesNotExist_ReturnsFalse() {
        // GIVEN
        String phone = "+573009998877";
        when(userRepository.existsByPhone(phone)).thenReturn(false);

        // WHEN
        boolean exists = userJpaAdapter.existsByPhoneNumber(phone);

        // THEN
        assertFalse(exists);
        verify(userRepository).existsByPhone(phone);
    }

    @Test
    @DisplayName("findById: Should return Optional of User if found")
    void findById_UserFound_ReturnsOptionalOfUser() {
        // GIVEN
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUser(userEntity)).thenReturn(userDomain.toBuilder().id(userId).build());

        // WHEN
        Optional<User> result = userJpaAdapter.findById(userId);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals(userDomain.getEmail(), result.get().getEmail());

        verify(userRepository).findById(userId);
        verify(userEntityMapper).toUser(userEntity);
    }

    @Test
    @DisplayName("findById: Should return Optional empty if user not found")
    void findById_UserNotFound_ReturnsOptionalEmpty() {
        // GIVEN
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // WHEN
        Optional<User> result = userJpaAdapter.findById(userId);

        // THEN
        assertFalse(result.isPresent());

        verify(userRepository).findById(userId);
        verify(userEntityMapper, never()).toUser(any(UserEntity.class)); // Mapper should not be called
    }

    @Test
    @DisplayName("findById: Should return Optional empty if ID is null")
    void findById_IdIsNull_ReturnsOptionalEmpty() {
        // WHEN
        Optional<User> result = userJpaAdapter.findById(null);

        // THEN
        assertFalse(result.isPresent());

        verify(userRepository, never()).findById(any(Long.class)); // Repository should not be called
        verify(userEntityMapper, never()).toUser(any(UserEntity.class)); // Mapper should not be called
    }
}
