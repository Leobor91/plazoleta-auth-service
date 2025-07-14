package com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserEntityTest {

    @Test
    @DisplayName("Should create a UserEntity object with all fields correctly initialized by constructor")
    void constructor_ShouldInitializeAllFields() {
        Long id = 1L;
        String name = "Juan";
        String lastName = "Perez";
        String identityDocument = "12345678";
        String phoneNumber = "+573101234567";
        String email = "juan@example.com";
        String password = "hashedPassword123";
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        RoleEntity role = new RoleEntity(1L, "CLIENTE", "Rol de cliente en DB");

        UserEntity userEntity = new UserEntity(id, name, lastName, identityDocument, phoneNumber, email, password, birthDate, role);

        assertEquals(id, userEntity.getId());
        assertEquals(name, userEntity.getName());
        assertEquals(lastName, userEntity.getLastName());
        assertEquals(identityDocument, userEntity.getIdentityDocument());
        assertEquals(phoneNumber, userEntity.getPhoneNumber());
        assertEquals(email, userEntity.getEmail());
        assertEquals(password, userEntity.getPassword());
        assertEquals(birthDate, userEntity.getBirthDate());
        assertEquals(role, userEntity.getRole());
        assertNotNull(userEntity.getRole());
        assertEquals("CLIENTE", userEntity.getRole().getName());
    }

    @Test
    @DisplayName("Should correctly set and get fields using setters and getters")
    void settersAndGetters_ShouldWorkCorrectly() {
        UserEntity userEntity = new UserEntity();
        Long id = 2L;
        String name = "Maria";
        String lastName = "Gomez";
        String identityDocument = "87654321";
        String phoneNumber = "+573209876543";
        String email = "maria@example.com";
        String password = "anotherHashedPassword";
        LocalDate birthDate = LocalDate.of(1985, 10, 20);
        RoleEntity newRole = new RoleEntity(2L, "PROPIETARIO", "Rol de propietario en DB");

        userEntity.setId(id);
        userEntity.setName(name);
        userEntity.setLastName(lastName);
        userEntity.setIdentityDocument(identityDocument);
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setBirthDate(birthDate);
        userEntity.setRole(newRole);

        assertEquals(id, userEntity.getId());
        assertEquals(name, userEntity.getName());
        assertEquals(lastName, userEntity.getLastName());
        assertEquals(identityDocument, userEntity.getIdentityDocument());
        assertEquals(phoneNumber, userEntity.getPhoneNumber());
        assertEquals(email, userEntity.getEmail());
        assertEquals(password, userEntity.getPassword());
        assertEquals(birthDate, userEntity.getBirthDate());
        assertEquals(newRole, userEntity.getRole());
    }

    @Test
    @DisplayName("Equals and HashCode should work correctly for UserEntity objects")
    void equalsAndHashCode_ShouldBeConsistent() {
        RoleEntity role1 = new RoleEntity(1L, "CLIENTE", "Rol de cliente");
        RoleEntity role2 = new RoleEntity(1L, "CLIENTE", "Rol de cliente");

        UserEntity userEntity1 = UserEntity.builder()
                .id(1L)
                .name("Juan")
                .lastName("Perez")
                .identityDocument("12345678")
                .phoneNumber("+57310")
                .email("juan@example.com")
                .password("pass")
                .birthDate(LocalDate.of(1990,1,1))
                .role(role1)
                .build();

        UserEntity userEntity2 = UserEntity.builder()
                .id(1L)
                .name("Juan")
                .lastName("Perez")
                .identityDocument("12345678")
                .phoneNumber("+57310")
                .email("juan@example.com")
                .password("pass")
                .birthDate(LocalDate.of(1990,1,1))
                .role(role2)
                .build();

        UserEntity userEntity3 = UserEntity.builder()
                .id(2L)
                .name("Pedro")
                .lastName("Gomez")
                .identityDocument("87654321")
                .phoneNumber("+57320")
                .email("pedro@example.com")
                .password("pass2")
                .birthDate(LocalDate.of(1991,2,2))
                .role(role1)
                .build();

        assertEquals(userEntity1, userEntity2);
        assertEquals(userEntity1.hashCode(), userEntity2.hashCode());

        assertNotEquals(userEntity1, userEntity3);
        assertNotEquals(userEntity1.hashCode(), userEntity3.hashCode());
    }

    @Test
    @DisplayName("ToString should return expected format for UserEntity")
    void toString_ShouldReturnExpectedFormat() {
        RoleEntity roleEntity = new RoleEntity(1L, "CLIENTE", "Rol de cliente");
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .name("Juan")
                .lastName("Perez")
                .identityDocument("12345678")
                .phoneNumber("+57310")
                .email("juan@example.com")
                .password("pass")
                .birthDate(LocalDate.of(1990,1,1))
                .role(roleEntity)
                .build();

        String expectedToString = "UserEntity(id=1, name=Juan, lastName=Perez, identityDocument=12345678, phoneNumber=+57310, email=juan@example.com, password=pass, birthDate=1990-01-01, role=RoleEntity(id=1, name=CLIENTE, description=Rol de cliente))";
        assertEquals(expectedToString, userEntity.toString());
    }
}
