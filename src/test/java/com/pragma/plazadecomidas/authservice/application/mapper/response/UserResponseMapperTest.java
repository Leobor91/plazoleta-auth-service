package com.pragma.plazadecomidas.authservice.application.mapper.response;

import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserResponseMapper;
import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserResponseMapperTest {


    private IUserResponseMapper userResponseMapper;

    @BeforeEach
    void setUp() {
        userResponseMapper = Mappers.getMapper(IUserResponseMapper.class);
    }

    @Test
    @DisplayName("Should map all fields correctly from User to UserResponseDto, excluding password")
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
                exampleRole.getName());

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

    @Test
    @DisplayName("Should return null when mapping a null User object")
    void toResponseDto_WithNullUser_ShouldReturnNull() {
        // WHEN
        UserResponseDto responseDto = userResponseMapper.toResponseDto(null);

        // THEN
        assertNull(responseDto);
    }

    @Test
    @DisplayName("Should correctly map a User object with null fields to UserResponseDto")
    void toResponseDto_ShouldHandleNullFieldsInUser() {
        // GIVEN
        User userWithNulls = new User(
                2L,
                "Pedro",
                null,
                "112233",
                null,
                "pedro@example.com",
                "somepass",
                null,
                null,
                null
        );

        // WHEN
        UserResponseDto responseDto = userResponseMapper.toResponseDto(userWithNulls);

        // THEN
        assertNotNull(responseDto);
        assertEquals(userWithNulls.getId(), responseDto.getId());
        assertEquals(userWithNulls.getName(), responseDto.getName());
        assertNull(responseDto.getLastName());
        assertEquals(userWithNulls.getIdentityDocument(), responseDto.getIdentityDocument());
        assertNull(responseDto.getPhoneNumber());
        assertEquals(userWithNulls.getEmail(), responseDto.getEmail());
        assertNull(responseDto.getBirthDate());
        assertNull(responseDto.getRole());
    }

}
