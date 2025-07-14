package com.pragma.plazadecomidas.authservice.application.mapper.request;

import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.application.mapper.IUserRequestMapper;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserRequestMapperTest {


    private IUserRequestMapper userRequestMapper;

    @BeforeEach
    void setUp() {

        userRequestMapper = Mappers.getMapper(IUserRequestMapper.class);

    }

    @Test
    void toUser_ShouldMapAllFieldsCorrectlyFromDto() {
        // GIVEN
        UserRequestDto requestDto = new UserRequestDto(
                "Fernando",
                "Lopez",
                "1020304050",
                "+573112223344",
                "fernando@example.com",
                "SecurePass123!",
                "1990-05-15"
        );

        // WHEN
        User user = userRequestMapper.toUser(requestDto);

        // THEN
        assertNotNull(user);
        assertEquals(requestDto.getName(), user.getName());
        assertEquals(requestDto.getLastName(), user.getLastName());
        assertEquals(requestDto.getEmail(), user.getEmail());
        assertEquals(requestDto.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(requestDto.getIdentityDocument(), user.getIdentityDocument());
        assertEquals(LocalDate.parse(requestDto.getBirthDate()), user.getBirthDate());
        assertEquals(requestDto.getPassword(), user.getPassword());
        assertNull(user.getId());
        assertNull(user.getRoleId());
    }

    @Test
    void toUser_WithNullRequestDto_ShouldReturnNull() {
        // WHEN
        User user = userRequestMapper.toUser(null);

        // THEN
        assertNull(user);
    }
}
