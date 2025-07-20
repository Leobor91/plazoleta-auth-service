package com.pragma.plazadecomidas.authservice.domain.usecase;

import com.pragma.plazadecomidas.authservice.domain.exception.DomainException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidAgeException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidDniFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidPhoneFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.NotFound;
import com.pragma.plazadecomidas.authservice.domain.exception.UserAlreadyExistsException;
import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.domain.spi.IRolePersistencePort;
import com.pragma.plazadecomidas.authservice.domain.spi.IUserPersistencePort;
import com.pragma.plazadecomidas.authservice.infrastructure.utils.ValidationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserUseCase userUseCase;

    private User ownerUser;
    private Role ownerRole;

    @BeforeEach
    void setUp() {
        ownerRole = Role.builder()
                .id(1L)
                .name(ValidationConstants.OWNER_ROLE_NAME)
                .description("Owner role")
                .build();

        ownerUser = User.builder()
                .id(null) // ID is null before saving
                .name("Juan")
                .lastName("Perez")
                .dniNumber("1234567890")
                .phone("+573101234567")
                .birthDate(LocalDate.now().minusYears(20)) // Adult user
                .email("juan.perez@example.com")
                .password("Password123!")
                .role(null) // Role is null before being set by the use case
                .build();
    }



    @Test
    @DisplayName("saveOwner: Should successfully save an owner user")
    void saveOwner_Success() {
        // GIVEN
        String encodedPassword = "encodedPassword123!";
        User userWithEncodedPassAndRole = ownerUser.toBuilder()
                .password(encodedPassword)
                .role(ownerRole)
                .build();
        User savedUser = ownerUser.toBuilder()
                .id(1L)
                .password(encodedPassword)
                .role(ownerRole)
                .build(); // User returned after saving

        // Mock dependencies
        when(userPersistencePort.existsByEmail(ownerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDniNumber(ownerUser.getDniNumber())).thenReturn(false);
        when(userPersistencePort.existsByPhoneNumber(ownerUser.getPhone())).thenReturn(false);
        when(rolePersistencePort.findByName(ValidationConstants.OWNER_ROLE_NAME)).thenReturn(Optional.of(ownerRole));
        when(passwordEncoder.encode(ownerUser.getPassword())).thenReturn(encodedPassword);
        when(userPersistencePort.saveUser(userWithEncodedPassAndRole)).thenReturn(savedUser);

        // WHEN
        User result = userUseCase.saveOwner(ownerUser);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(ownerRole.getName(), result.getRole().getName());

        // Verify interactions
        verify(userPersistencePort).existsByEmail(ownerUser.getEmail());
        verify(userPersistencePort).existsByDniNumber(ownerUser.getDniNumber());
        verify(userPersistencePort).existsByPhoneNumber(ownerUser.getPhone());
        verify(rolePersistencePort).findByName(ValidationConstants.OWNER_ROLE_NAME);
        verify(passwordEncoder).encode(ownerUser.getPassword());
        verify(userPersistencePort).saveUser(userWithEncodedPassAndRole);
    }

    @Test
    @DisplayName("saveOwner: Should throw InvalidAgeException if user is not an adult")
    void saveOwner_UserNotAdult_ThrowsInvalidAgeException() {
        // GIVEN
        User underageUser = ownerUser.toBuilder()
                .birthDate(LocalDate.now().minusYears(ValidationConstants.MIN_AGE_FOR_ADULT).plusDays(1)) // Underage
                .build();

        // WHEN & THEN
        InvalidAgeException exception = assertThrows(InvalidAgeException.class,
                () -> userUseCase.saveOwner(underageUser));

        assertEquals(ValidationConstants.USER_MUST_BE_ADULT_MESSAGE, exception.getMessage());

        // Verify no further interactions after exception
        verify(userPersistencePort, never()).existsByEmail(anyString());
        verify(rolePersistencePort, never()).findByName(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    @DisplayName("saveOwner: Should throw UserAlreadyExistsException if email already exists")
    void saveOwner_EmailAlreadyExists_ThrowsUserAlreadyExistsException() {
        // GIVEN
        when(userPersistencePort.existsByEmail(ownerUser.getEmail())).thenReturn(true);

        // WHEN & THEN
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> userUseCase.saveOwner(ownerUser));

        assertEquals(ValidationConstants.EMAIL_ALREADY_EXISTS_MESSAGE.concat(ownerUser.getEmail()), exception.getMessage());

        // Verify interactions
        verify(userPersistencePort).existsByEmail(ownerUser.getEmail());
        verify(userPersistencePort, never()).existsByDniNumber(anyString()); // Should not proceed to DNI check
    }

    @Test
    @DisplayName("saveOwner: Should throw UserAlreadyExistsException if DNI number already exists")
    void saveOwner_DniAlreadyExists_ThrowsUserAlreadyExistsException() {
        // GIVEN
        when(userPersistencePort.existsByEmail(ownerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDniNumber(ownerUser.getDniNumber())).thenReturn(true);

        // WHEN & THEN
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> userUseCase.saveOwner(ownerUser));

        assertEquals(ValidationConstants.DNI_ALREADY_EXISTS_MESSAGE.concat(ownerUser.getDniNumber()), exception.getMessage());

        // Verify interactions
        verify(userPersistencePort).existsByEmail(ownerUser.getEmail());
        verify(userPersistencePort).existsByDniNumber(ownerUser.getDniNumber());
        verify(userPersistencePort, never()).existsByPhoneNumber(anyString()); // Should not proceed to Phone check
    }

    @Test
    @DisplayName("saveOwner: Should throw UserAlreadyExistsException if phone number already exists")
    void saveOwner_PhoneAlreadyExists_ThrowsUserAlreadyExistsException() {
        // GIVEN
        when(userPersistencePort.existsByEmail(ownerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDniNumber(ownerUser.getDniNumber())).thenReturn(false);
        when(userPersistencePort.existsByPhoneNumber(ownerUser.getPhone())).thenReturn(true);

        // WHEN & THEN
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> userUseCase.saveOwner(ownerUser));

        assertEquals(ValidationConstants.PHONE_ALREADY_EXISTS_MESSAGE.concat(ownerUser.getPhone()), exception.getMessage());

        // Verify interactions
        verify(userPersistencePort).existsByEmail(ownerUser.getEmail());
        verify(userPersistencePort).existsByDniNumber(ownerUser.getDniNumber());
        verify(userPersistencePort).existsByPhoneNumber(ownerUser.getPhone());
        verify(rolePersistencePort, never()).findByName(anyString()); // Should not proceed to role lookup
    }

    @Test
    @DisplayName("saveOwner: Should throw InvalidDniFormatException if DNI format is invalid")
    void saveOwner_InvalidDniFormat_ThrowsInvalidDniFormatException() {
        // GIVEN
        User invalidDniUser = ownerUser.toBuilder().dniNumber("INVALID_DNI").build(); // Invalid DNI format
        when(userPersistencePort.existsByEmail(invalidDniUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDniNumber(invalidDniUser.getDniNumber())).thenReturn(false);
        when(userPersistencePort.existsByPhoneNumber(invalidDniUser.getPhone())).thenReturn(false);


        // WHEN & THEN
        InvalidDniFormatException exception = assertThrows(InvalidDniFormatException.class,
                () -> userUseCase.saveOwner(invalidDniUser));

        assertEquals(ValidationConstants.DNI_NUMBER_FORMAT_MESSAGE, exception.getMessage());

        // Verify interactions up to the point of failure
        verify(userPersistencePort).existsByEmail(invalidDniUser.getEmail());
        verify(userPersistencePort).existsByDniNumber(invalidDniUser.getDniNumber());
        verify(userPersistencePort).existsByPhoneNumber(invalidDniUser.getPhone());
        verify(rolePersistencePort, never()).findByName(anyString());
    }

    @Test
    @DisplayName("saveOwner: Should throw InvalidPhoneFormatException if phone format is invalid")
    void saveOwner_InvalidPhoneFormat_ThrowsInvalidPhoneFormatException() {
        // GIVEN
        User invalidPhoneUser = ownerUser.toBuilder().phone("INVALID_PHONE").build(); // Invalid Phone format
        when(userPersistencePort.existsByEmail(invalidPhoneUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDniNumber(invalidPhoneUser.getDniNumber())).thenReturn(false);
        when(userPersistencePort.existsByPhoneNumber(invalidPhoneUser.getPhone())).thenReturn(false);


        // WHEN & THEN
        InvalidPhoneFormatException exception = assertThrows(InvalidPhoneFormatException.class,
                () -> userUseCase.saveOwner(invalidPhoneUser));

        assertEquals(ValidationConstants.PHONE_FORMAT_MESSAGE, exception.getMessage());

        // Verify interactions up to the point of failure
        verify(userPersistencePort).existsByEmail(invalidPhoneUser.getEmail());
        verify(userPersistencePort).existsByDniNumber(invalidPhoneUser.getDniNumber());
        verify(userPersistencePort).existsByPhoneNumber(invalidPhoneUser.getPhone());
        verify(rolePersistencePort, never()).findByName(anyString());
    }

    @Test
    @DisplayName("saveOwner: Should throw NotFound if OWNER role is not found")
    void saveOwner_OwnerRoleNotFound_ThrowsNotFound() {
        // GIVEN
        when(userPersistencePort.existsByEmail(ownerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDniNumber(ownerUser.getDniNumber())).thenReturn(false);
        when(userPersistencePort.existsByPhoneNumber(ownerUser.getPhone())).thenReturn(false);
        when(rolePersistencePort.findByName(ValidationConstants.OWNER_ROLE_NAME)).thenReturn(Optional.empty());

        // WHEN & THEN
        NotFound exception = assertThrows(NotFound.class,
                () -> userUseCase.saveOwner(ownerUser));

        assertEquals(ValidationConstants.ROLE_NOT_FOUND_MESSAGE, exception.getMessage());

        // Verify interactions
        verify(userPersistencePort).existsByEmail(ownerUser.getEmail());
        verify(userPersistencePort).existsByDniNumber(ownerUser.getDniNumber());
        verify(userPersistencePort).existsByPhoneNumber(ownerUser.getPhone());
        verify(rolePersistencePort).findByName(ValidationConstants.OWNER_ROLE_NAME);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    @DisplayName("saveOwner: Should throw DomainException if user persistence fails (returns null)")
    void saveOwner_UserPersistenceSaveReturnsNull_ThrowsDomainException() {
        // GIVEN
        String encodedPassword = "encodedPassword123!";
        User userWithEncodedPassAndRole = ownerUser.toBuilder()
                .password(encodedPassword)
                .role(ownerRole)
                .build();

        when(userPersistencePort.existsByEmail(ownerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDniNumber(ownerUser.getDniNumber())).thenReturn(false);
        when(userPersistencePort.existsByPhoneNumber(ownerUser.getPhone())).thenReturn(false);
        when(rolePersistencePort.findByName(ValidationConstants.OWNER_ROLE_NAME)).thenReturn(Optional.of(ownerRole));
        when(passwordEncoder.encode(ownerUser.getPassword())).thenReturn(encodedPassword);
        when(userPersistencePort.saveUser(userWithEncodedPassAndRole)).thenReturn(null); // Simulate save failure

        // WHEN & THEN
        DomainException exception = assertThrows(DomainException.class,
                () -> userUseCase.saveOwner(ownerUser));

        assertEquals(ValidationConstants.NOT_SAVED_MESSAGE, exception.getMessage());

        // Verify interactions
        verify(userPersistencePort).existsByEmail(ownerUser.getEmail());
        verify(userPersistencePort).existsByDniNumber(ownerUser.getDniNumber());
        verify(userPersistencePort).existsByPhoneNumber(ownerUser.getPhone());
        verify(rolePersistencePort).findByName(ValidationConstants.OWNER_ROLE_NAME);
        verify(passwordEncoder).encode(ownerUser.getPassword());
        verify(userPersistencePort).saveUser(userWithEncodedPassAndRole);
    }


    // --- isOwner tests ---

    @Test
    @DisplayName("isOwner: Should return User if user exists and role is found")
    void isOwner_UserExistsAndRoleFound_ReturnsUser() {
        // GIVEN
        Long userId = 1L;
        User foundUser = ownerUser.toBuilder().id(userId).role(ownerRole).build(); // Assume this user is found by ID
        Role retrievedRole = Role.builder().id(ownerRole.getId()).name(ownerRole.getName()).description(ownerRole.getDescription()).build();

        when(userPersistencePort.findById(userId)).thenReturn(Optional.of(foundUser));
        when(rolePersistencePort.findById(foundUser.getRole().getId())).thenReturn(Optional.of(retrievedRole));

        // WHEN
        User result = userUseCase.isOwner(userId);

        // THEN
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(ownerRole.getName(), result.getRole().getName());
        assertEquals(ownerRole.getDescription(), result.getRole().getDescription());

        // Verify interactions
        verify(userPersistencePort).findById(userId);
        verify(rolePersistencePort).findById(foundUser.getRole().getId());
    }

    @Test
    @DisplayName("isOwner: Should throw NotFound if user is not found by ID")
    void isOwner_UserNotFound_ThrowsNotFound() {
        // GIVEN
        Long userId = 99L;
        when(userPersistencePort.findById(userId)).thenReturn(Optional.empty());

        // WHEN & THEN
        NotFound exception = assertThrows(NotFound.class,
                () -> userUseCase.isOwner(userId));

        assertEquals(ValidationConstants.USER_NOT_FOUND_MESSAGE, exception.getMessage());

        // Verify interactions
        verify(userPersistencePort).findById(userId);
        verify(rolePersistencePort, never()).findById(anyLong()); // Role persistence should not be called
    }

    @Test
    @DisplayName("isOwner: Should throw NotFound if user found but role is not found")
    void isOwner_UserFoundButRoleNotFound_ThrowsNotFound() {
        // GIVEN
        Long userId = 1L;
        User foundUser = ownerUser.toBuilder().id(userId).role(ownerRole).build(); // User found, but assume its role is not found
        when(userPersistencePort.findById(userId)).thenReturn(Optional.of(foundUser));
        when(rolePersistencePort.findById(foundUser.getRole().getId())).thenReturn(Optional.empty());

        // WHEN & THEN
        NotFound exception = assertThrows(NotFound.class,
                () -> userUseCase.isOwner(userId));

        assertEquals(ValidationConstants.ROLE_NOT_FOUND_MESSAGE, exception.getMessage());

        // Verify interactions
        verify(userPersistencePort).findById(userId);
        verify(rolePersistencePort).findById(foundUser.getRole().getId());
    }
}
