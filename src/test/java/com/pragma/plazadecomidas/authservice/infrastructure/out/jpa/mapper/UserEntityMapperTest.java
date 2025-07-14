package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper;

import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    private IUserEntityMapper userEntityMapper;

    @BeforeEach
    void setUp() {
        userEntityMapper = Mappers.getMapper(IUserEntityMapper.class);
    }

    @Test
    void toEntity_ShouldMapAllFieldsCorrectly() {
        // GIVEN

        User user = new User(1L, "Juan", "Perez", "12345678", "+573101234567", "juan@example.com", "password", LocalDate.of(1990, 5, 15), "1", "CLIENTE");


        // WHEN
        UserEntity userEntity = userEntityMapper.toUserEntity(user);

        // THEN
        assertNotNull(userEntity);
        assertEquals(user.getName(), userEntity.getName());
        assertEquals(user.getLastName(), userEntity.getLastName());
        assertEquals(user.getIdentityDocument(), userEntity.getIdentityDocument());
        assertEquals(user.getPhoneNumber(), userEntity.getPhoneNumber());
        assertEquals(user.getEmail(), userEntity.getEmail());
        assertEquals(user.getPassword(), userEntity.getPassword());
        assertEquals(user.getBirthDate(), userEntity.getBirthDate());


    }

    @Test
    void toUser_ShouldMapAllFieldsCorrectly() {
        // GIVEN
        UserEntity userEntity = new UserEntity(1L, "Maria", "Gomez", "87654321", "+573209876543", "maria@example.com", "hashed_password", LocalDate.of(1985, 10, 20), null); // <-- Agregada fecha


        // WHEN
        User user = userEntityMapper.toUser(userEntity);

        // THEN
        assertNotNull(user);
        assertEquals(userEntity.getName(), user.getName());
        assertEquals(userEntity.getLastName(), user.getLastName());
        assertEquals(userEntity.getIdentityDocument(), user.getIdentityDocument());
        assertEquals(userEntity.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getPassword(), user.getPassword());
        assertEquals(userEntity.getBirthDate(), user.getBirthDate());
    }


    @Test
    void toEntity_WithNullInput_ShouldReturnNull() {
        UserEntity userEntity = userEntityMapper.toUserEntity(null);
        assertNull(userEntity);
    }

    @Test
    void toUser_WithNullInput_ShouldReturnNull() {
        User user = userEntityMapper.toUser(null);
        assertNull(user);
    }
}
