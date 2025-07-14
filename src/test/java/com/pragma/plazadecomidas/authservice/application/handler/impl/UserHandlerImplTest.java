package com.pragma.plazadecomidas.authservice.application.handler.impl;


import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserRequestMapper;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserResponseMapper;
import com.pragma.plazadecomidas.authservice.domain.api.IUserServicePort;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedException;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.domain.util.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserHandlerImplTest {

    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IUserRequestMapper userRequestMapper;
    @Mock
    private IUserResponseMapper userResponseMapper;
    @Mock
    private ValidationUtils validationUtils;

    @InjectMocks
    private UserHandlerImpl userHandler;

    private UserRequestDto userRequestDto;
    private User domainUser;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        userRequestDto = new UserRequestDto(
                "Juan",
                "Perez",
                "juan@example.com",
                "+573101234567",
                "123456789",
                "Password123*",
                "1990, 1, 1"
        );

        domainUser = new User(
                null,
                "Juan",
                "Perez",
                "123456789",
                "+573101234567",
                "juan@example.com",
                "Password123*",
                birthDate,
                "PROPIETARIO",
                "Descripción del propietario"
        );

        userResponseDto = new UserResponseDto(
                1L,
                "Juan",
                "Perez",
                "juan@example.com",
                "+573101234567",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "PROPIETARIO"
        );

        when(validationUtils.isValidDateFormat(any(String.class))).thenReturn(true);
    }

    @Test
    void saveOwner_ShouldReturnUserResponseDtoWhenSuccessful() {
        // GIVEN
        when(userRequestMapper.toUser(userRequestDto)).thenReturn(domainUser);
        when(userServicePort.saveOwner(domainUser)).thenReturn(domainUser);
        when(userResponseMapper.toResponseDto(domainUser)).thenReturn(userResponseDto);

        // WHEN
        UserResponseDto result = userHandler.saveOwner(userRequestDto);

        // THEN
        assertNotNull(result);
        assertEquals(userResponseDto.getId(), result.getId());
        assertEquals(userResponseDto.getName(), result.getName());
        assertEquals(userResponseDto.getEmail(), result.getEmail());

        verify(userRequestMapper, times(1)).toUser(userRequestDto);
        verify(userServicePort, times(1)).saveOwner(domainUser);
        verify(userResponseMapper, times(1)).toResponseDto(domainUser);
    }

    @Test
    void saveOwner_ShouldThrowExceptionWhenServicePortThrowsException() {
        // GIVEN
        when(userRequestMapper.toUser(userRequestDto)).thenReturn(domainUser);
        when(userServicePort.saveOwner(domainUser)).thenThrow(new IllegalArgumentException("El email ya está registrado"));

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> userHandler.saveOwner(userRequestDto));

        verify(userRequestMapper, times(1)).toUser(userRequestDto);
        verify(userServicePort, times(1)).saveOwner(domainUser);
        verify(userResponseMapper, never()).toResponseDto(any(User.class));
    }

    @Test
    void saveOwner_ShouldThrowPersonalizedExceptionWhenBirthDateFormatIsInvalid() {
        // GIVEN
        when(validationUtils.isValidDateFormat(userRequestDto.getBirthDate())).thenReturn(false);

        // WHEN & THEN
        assertThrows(PersonalizedException.class, () -> userHandler.saveOwner(userRequestDto));

        verify(userRequestMapper, never()).toUser(any());
        verify(userServicePort, never()).saveOwner(any());
        verify(userResponseMapper, never()).toResponseDto(any());
    }
}
