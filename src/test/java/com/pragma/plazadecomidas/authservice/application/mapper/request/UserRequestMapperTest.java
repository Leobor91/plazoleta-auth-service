package com.pragma.plazadecomidas.authservice.application.mapper.request;

import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserRequestMapper;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserResponseMapper;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserRequestMapperTest {

    private final IUserRequestMapper userRequestMapper = Mappers.getMapper(IUserRequestMapper.class);
    private  final IUserResponseMapper userResponseMapper = Mappers.getMapper(IUserResponseMapper.class);

    @Test
    @DisplayName("Debería mapear UserRequestDto a User correctamente")
    void toUser_ShouldMapDtoToModelCorrectly() {
        UserRequestDto requestDto = UserRequestDto.builder()
                .name("TestName")
                .lastName("TestLastName")
                .identityDocument("12345")
                .phoneNumber("+1234567890")
                .email("test@example.com")
                .password("securePassword")
                .birthDate("1990-01-01")
                .build();

        User user = userRequestMapper.toUser(requestDto);

        assertNotNull(user);
        assertEquals(requestDto.getName(), user.getName());
        assertEquals(requestDto.getLastName(), user.getLastName());
        assertEquals(requestDto.getIdentityDocument(), user.getIdentityDocument());
        assertEquals(requestDto.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(requestDto.getEmail(), user.getEmail());
        assertEquals(requestDto.getPassword(), user.getPassword());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthDate());
        assertNull(user.getId());
        assertNull(user.getRoleName());
    }

    @Test
    @DisplayName("Debería mapear User a UserResponseDto correctamente")
    void toResponseDto_ShouldMapModelToDtoCorrectly() {
        // GIVEN
        User user = User.builder()
                .id(1L)
                .name("TestName")
                .lastName("TestLastName")
                .identityDocument("12345")
                .phoneNumber("+1234567890")
                .email("test@example.com")
                .password("encodedPassword") // Password should not be mapped to response DTO
                .birthDate(LocalDate.of(1990, 1, 1))
                .roleName("PROPIETARIO")
                .build();

        // WHEN
        UserResponseDto responseDto = userResponseMapper.toResponseDto(user);

        // THEN
        assertNotNull(responseDto);
        assertEquals(user.getId(), responseDto.getId());
        assertEquals(user.getName(), responseDto.getName());
        assertEquals(user.getLastName(), responseDto.getLastName());
        assertEquals(user.getIdentityDocument(), responseDto.getIdentityDocument());
        assertEquals(user.getPhoneNumber(), responseDto.getPhoneNumber());
        assertEquals(user.getEmail(), responseDto.getEmail());
        assertEquals(user.getBirthDate(), responseDto.getBirthDate());
        assertEquals(user.getRoleName(), responseDto.getRole());
    }
}
