package com.pragma.plazadecomidas.authservice.domain.api;

import com.pragma.plazadecomidas.authservice.domain.api.impl.UserServicePortImpl;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedBadRequestException;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedException;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedNotFoundException;
import com.pragma.plazadecomidas.authservice.domain.model.MessageEnum;
import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.domain.spi.IRolePersistencePort;
import com.pragma.plazadecomidas.authservice.domain.spi.IUserPersistencePort;
import com.pragma.plazadecomidas.authservice.domain.util.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServicePortImplTest {

    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private IRolePersistencePort rolePersistencePort;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private UserServicePortImpl userServicePort;

    private User validUser;
    private Role ownerRole;
    @BeforeEach
    void setUp() {
        ownerRole = new Role(2L, MessageEnum.PROPIETARIO.getMessage(), "Role for owner");

        validUser = User.builder()
                .name("Juan")
                .lastName("Perez")
                .identityDocument("1234567890")
                .phoneNumber("+573101234567")
                .email("juan.perez@example.com")
                .password("Password123!")
                .birthDate(LocalDate.of(1990, 5, 15))
                .build();

        when(validationUtils.isValid(anyString())).thenReturn(true);
        when(validationUtils.containsOnlyNumbers(anyString())).thenReturn(true);
        when(validationUtils.isValidPhoneStructure(anyString())).thenReturn(true);
        when(validationUtils.isValidEmailStructure(anyString())).thenReturn(true);
        when(validationUtils.isAdult(any(LocalDate.class))).thenReturn(true);
        when(validationUtils.isBirthDateNotFuture(any(LocalDate.class))).thenReturn(true);


        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByPhone(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByIdentification(anyString())).thenReturn(Optional.empty());


        when(rolePersistencePort.findByName(MessageEnum.PROPIETARIO.getMessage())).thenReturn(Optional.of(ownerRole));


        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");


        when(userPersistencePort.save(any(User.class))).thenAnswer(invocation -> {
            User userArg = invocation.getArgument(0);
            return userArg.toBuilder().id(1L).build(); // Simula que el usuario es guardado y se le asigna un ID
        });
    }

    @Test
    @DisplayName("Should save owner successfully when all validations pass and user does not exist")
    void saveOwner_Success() {
        User savedUser = userServicePort.saveOwner(validUser);

        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        assertEquals("hashedPassword", savedUser.getPassword());
        assertEquals(String.valueOf(ownerRole.getId()), savedUser.getRoleId());
        assertEquals(ownerRole.getName(), savedUser.getRoleName());
        verify(userPersistencePort).findByEmail(validUser.getEmail());
        verify(userPersistencePort).findByPhone(validUser.getPhoneNumber());
        verify(userPersistencePort).findByIdentification(validUser.getIdentityDocument());
        verify(rolePersistencePort).findByName(MessageEnum.PROPIETARIO.getMessage());
        verify(passwordEncoder).encode(validUser.getPassword());
        verify(userPersistencePort).save(any(User.class));
    }


    @Test
    @DisplayName("Should throw PersonalizedException when name is invalid")
    void saveOwner_InvalidName_ThrowsException() {
        when(validationUtils.isValid(validUser.getName())).thenReturn(false);

        PersonalizedBadRequestException exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.NAME_REQUIRED.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when lastName is invalid")
    void saveOwner_InvalidLastName_ThrowsException() {
        when(validationUtils.isValid(validUser.getLastName())).thenReturn(false);

        PersonalizedBadRequestException  exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.LASTNAME_REQUIRED.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when identityDocument is invalid")
    void saveOwner_InvalidIdentityDocument_ThrowsException() {
        when(validationUtils.isValid(validUser.getIdentityDocument())).thenReturn(false);

        PersonalizedBadRequestException  exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.DOCUMENT_REQUIRED.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when identityDocument format is invalid")
    void saveOwner_InvalidIdentityDocumentFormat_ThrowsException() {
        when(validationUtils.containsOnlyNumbers(validUser.getIdentityDocument())).thenReturn(false);

        PersonalizedBadRequestException  exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.DOCUMENT_FORMAT.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when phoneNumber is invalid")
    void saveOwner_InvalidPhoneNumber_ThrowsException() {
        when(validationUtils.isValid(validUser.getPhoneNumber())).thenReturn(false);

        PersonalizedBadRequestException  exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.PHONE_REQUIRED.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when phoneNumber format is invalid")
    void saveOwner_InvalidPhoneNumberFormat_ThrowsException() {
        when(validationUtils.isValidPhoneStructure(validUser.getPhoneNumber())).thenReturn(false);

        PersonalizedBadRequestException  exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.PHONE_FORMAT.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when email is invalid")
    void saveOwner_InvalidEmail_ThrowsException() {
        when(validationUtils.isValid(validUser.getEmail())).thenReturn(false);

        PersonalizedBadRequestException  exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.EMAIL_REQUIRED.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when email format is invalid")
    void saveOwner_InvalidEmailFormat_ThrowsException() {
        when(validationUtils.isValidEmailStructure(validUser.getEmail())).thenReturn(false);

        PersonalizedBadRequestException  exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.EMAIL_FORMAT.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when password is invalid")
    void saveOwner_InvalidPassword_ThrowsException() {
        when(validationUtils.isValid(validUser.getPassword())).thenReturn(false);

        PersonalizedBadRequestException  exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.PASSWORD_REQUIRED.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when birthDate is null")
    void saveOwner_NullBirthDate_ThrowsException() {
        validUser.setBirthDate(null); // Set to null to trigger validation

        PersonalizedBadRequestException exception = assertThrows(PersonalizedBadRequestException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.BIRTHDATE_REQUIRED.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when user is not an adult")
    void saveOwner_NotAdult_ThrowsException() {
        when(validationUtils.isAdult(any(LocalDate.class))).thenReturn(false);

        PersonalizedException exception = assertThrows(PersonalizedException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.BIRTHDATE_ADULT.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when birthDate is in the future")
    void saveOwner_BirthDateFuture_ThrowsException() {
        when(validationUtils.isBirthDateNotFuture(any(LocalDate.class))).thenReturn(false);

        PersonalizedException exception = assertThrows(PersonalizedException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.BIRTHDATE_PAST.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }


    @Test
    @DisplayName("Should throw PersonalizedException when email already exists")
    void saveOwner_EmailAlreadyExists_ThrowsException() {
        when(userPersistencePort.findByEmail(validUser.getEmail())).thenReturn(Optional.of(validUser));

        PersonalizedException exception = assertThrows(PersonalizedException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.EMAIL_ALREADY_EXISTS.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when phone number already exists")
    void saveOwner_PhoneNumberAlreadyExists_ThrowsException() {
        when(userPersistencePort.findByPhone(validUser.getPhoneNumber())).thenReturn(Optional.of(validUser));

        PersonalizedException exception = assertThrows(PersonalizedException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.PHONE_ALREADY_EXISTS.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw PersonalizedException when identity document already exists")
    void saveOwner_IdentityDocumentAlreadyExists_ThrowsException() {
        when(userPersistencePort.findByIdentification(validUser.getIdentityDocument())).thenReturn(Optional.of(validUser));

        PersonalizedException exception = assertThrows(PersonalizedException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.DOCUMENT_ALREADY_EXISTS.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }


    @Test
    @DisplayName("Should throw PersonalizedException when owner role is not found")
    void saveOwner_OwnerRoleNotFound_ThrowsException() {
        when(rolePersistencePort.findByName(MessageEnum.PROPIETARIO.getMessage())).thenReturn(Optional.empty());

        PersonalizedNotFoundException exception = assertThrows(PersonalizedNotFoundException.class, () ->
                userServicePort.saveOwner(validUser));

        assertEquals(MessageEnum.ROLE_NOT_FOUND.getMessage(), exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }
}

