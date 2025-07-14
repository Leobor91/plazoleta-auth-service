package com.pragma.plazadecomidas.authservice.application.mapper.response;

import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserResponseMapper;
import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserResponseMapperTest {

    private IUserResponseMapper userResponseMapper;

    @BeforeEach
    void setUp() {
        userResponseMapper = Mappers.getMapper(IUserResponseMapper.class);
    }

    @Test
    void toResponseDto_ShouldMapAllFieldsCorrectlyFromUser() {
        // GIVEN
        LocalDate birthDate = LocalDate.of(1985, 1, 1);
        Role exampleRole = new Role(1L, "ADMIN", "Administrator Role");
        User user = new User(
                1L,
                "Ana",
                "Gomez",
                "987654321",
                "+573221112233",
                "ana@example.com",
                "hashedPassword",
                birthDate,
                String.valueOf(exampleRole.getId()),
                exampleRole.getName()
        );

        // WHEN
        UserResponseDto responseDto = userResponseMapper.toResponseDto(user);

        // THEN
        assertNotNull(responseDto);
        assertEquals(user.getId(), responseDto.getId());
        assertEquals(user.getName(), responseDto.getName());
        assertEquals(user.getLastName(), responseDto.getLastName());
        assertEquals(user.getEmail(), responseDto.getEmail());
    }

    @Test
    void toResponseDto_WithNullUser_ShouldReturnNull() {
        // WHEN
        UserResponseDto responseDto = userResponseMapper.toResponseDto(null);

        // THEN
        assertNull(responseDto);
    }

}
