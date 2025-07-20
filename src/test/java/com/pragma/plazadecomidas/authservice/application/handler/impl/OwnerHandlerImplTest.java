package com.pragma.plazadecomidas.authservice.application.handler.impl;

import com.pragma.plazadecomidas.authservice.application.dto.request.OwnerRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.mapper.IOwnerRequestMapper;
import com.pragma.plazadecomidas.authservice.domain.api.IUserServicePort;
import com.pragma.plazadecomidas.authservice.domain.exception.NotFound;
import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.infrastructure.utils.ValidationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;;

@ExtendWith(MockitoExtension.class)
class OwnerHandlerImplTest {


    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IOwnerRequestMapper ownerRequestMapper;

    @InjectMocks
    private OwnerHandlerImpl ownerHandler;

    private OwnerRequestDto ownerRequestDto;
    private User userDomainModel;
    private UserResponseDto userResponseDto;
    private User existingUserDomainModel;

    @BeforeEach
    void setUp() {
        // Initialize DTO and Domain Models for testing
        ownerRequestDto = new OwnerRequestDto(
                "Juan",
                "Perez",
                "123456789",
                "+573101234567",
                LocalDate.of(1990, 1, 1),
                "juan.perez@example.com",
                "password123"
        );

        userDomainModel = new User(
                null,
                "Juan",
                "Perez",
                "123456789",
                "+573101234567",
                LocalDate.of(1990, 1, 1),
                "juan.perez@example.com",
                "password123",
                null
        );

        userResponseDto = new UserResponseDto(
                1L,
                "Juan",
                "Perez",
                "123456789",
                "+573101234567",
                LocalDate.of(1990, 1, 1),
                "juan.perez@example.com",
                "ROLE_OWNER"
        );

        existingUserDomainModel = new User(
                1L,
                "Existing",
                "Owner",
                "987654321",
                "+573209876543",
                LocalDate.of(1985, 1, 1),
                "existing.owner@example.com",
                "ownerPass",
                new Role(4L, "CLIENT", "Client Role")
        );
    }

    @Test
    @DisplayName("saveOwner: Should save owner and return UserResponseDto on success")
    void saveOwner_Success() {
        // GIVEN
        when(ownerRequestMapper.toUser(ownerRequestDto)).thenReturn(userDomainModel);
        when(userServicePort.saveOwner(userDomainModel)).thenReturn(userDomainModel); // Assuming saveOwner returns the saved User
        when(ownerRequestMapper.toResponseDto(userDomainModel)).thenReturn(userResponseDto);

        // WHEN
        UserResponseDto result = ownerHandler.saveOwner(ownerRequestDto);

        // THEN
        assertNotNull(result);
        assertEquals(userResponseDto.getDniNumber(), result.getDniNumber());
        assertEquals(userResponseDto.getEmail(), result.getEmail());
        assertEquals(userResponseDto.getRoleName(), result.getRoleName());

        // Verify that the correct methods were called in order
        verify(ownerRequestMapper).toUser(ownerRequestDto);
        verify(userServicePort).saveOwner(userDomainModel);
        verify(ownerRequestMapper).toResponseDto(userDomainModel);
    }

    @Test
    @DisplayName("saveOwner: Should throw NotFound exception if ownerRequestDto is null")
    void saveOwner_NullOwnerRequestDto_ThrowsNotFound() {
        // WHEN & THEN
        NotFound exception = assertThrows(NotFound.class, () -> ownerHandler.saveOwner(null));
        assertEquals(ValidationConstants.USER_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("saveOwner: Should throw NotFound exception if mapping to User fails (returns null)")
    void saveOwner_MapperToUserReturnsNull_ThrowsNotFound() {
        // GIVEN
        when(ownerRequestMapper.toUser(ownerRequestDto)).thenReturn(null);

        // WHEN & THEN
        NotFound exception = assertThrows(NotFound.class, () -> ownerHandler.saveOwner(ownerRequestDto));
        assertEquals(ValidationConstants.USER_NOT_FOUND_MESSAGE, exception.getMessage());
        // Verify that userServicePort was NOT called
        verify(userServicePort, org.mockito.Mockito.never()).saveOwner(any());
    }

    @Test
    @DisplayName("saveOwner: Should throw NotFound exception if saving user fails (returns null)")
    void saveOwner_UserServicePortSaveOwnerReturnsNull_ThrowsNotFound() {
        // GIVEN
        when(ownerRequestMapper.toUser(ownerRequestDto)).thenReturn(userDomainModel);
        when(userServicePort.saveOwner(userDomainModel)).thenReturn(null);

        // WHEN & THEN
        NotFound exception = assertThrows(NotFound.class, () -> ownerHandler.saveOwner(ownerRequestDto));
        assertEquals(ValidationConstants.USER_NOT_FOUND_MESSAGE, exception.getMessage());
        // Verify that toResponseDto was NOT called
        verify(ownerRequestMapper, org.mockito.Mockito.never()).toResponseDto(any());
    }


    @Test
    @DisplayName("isOwner: Should return UserResponseDto for a valid owner ID")
    void isOwner_ValidId_ReturnsUserResponseDto() {
        // GIVEN
        Long ownerId = 1L;
        when(userServicePort.isOwner(ownerId)).thenReturn(existingUserDomainModel);
        when(ownerRequestMapper.toResponseDto(existingUserDomainModel)).thenReturn(userResponseDto);

        // WHEN
        UserResponseDto result = ownerHandler.isOwner(ownerId);

        // THEN
        assertNotNull(result);
        assertEquals(userResponseDto.getDniNumber(), result.getDniNumber());
        assertEquals(userResponseDto.getEmail(), result.getEmail());
        assertEquals(userResponseDto.getRoleName(), result.getRoleName());

        // Verify calls
        verify(userServicePort).isOwner(ownerId);
        verify(ownerRequestMapper).toResponseDto(existingUserDomainModel);
    }

    @Test
    @DisplayName("isOwner: Should propagate exception if userServicePort.isOwner throws one")
    void isOwner_UserServicePortThrowsException_ExceptionIsPropagated() {
        // GIVEN
        Long invalidId = 99L;
        when(userServicePort.isOwner(invalidId)).thenThrow(new NotFound("Owner not found for ID: " + invalidId));

        // WHEN & THEN
        NotFound exception = assertThrows(NotFound.class, () -> ownerHandler.isOwner(invalidId));
        assertEquals("Owner not found for ID: " + invalidId, exception.getMessage());

        // Verify calls
        verify(userServicePort).isOwner(invalidId);
        verify(ownerRequestMapper, org.mockito.Mockito.never()).toResponseDto(any());
    }

}
