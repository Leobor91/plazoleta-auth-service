package com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserEntityTest {
    private UserEntity user1;
    private UserEntity user2;
    private UserEntity user3;
    private RoleEntity role;

    @BeforeEach
    void setUp() {
        role = new RoleEntity(1L, "PROPIETARIO", "Descripción del propietario");

        user1 = new UserEntity(
                1L, "Juan", "Perez", "123", "+57", "juan@example.com",
                "pass", LocalDate.of(1990, 1, 1), role
        );

        user2 = new UserEntity(
                1L, "Juan", "Perez", "123", "+57", "juan@example.com",
                "pass", LocalDate.of(1990, 1, 1), role
        );

        user3 = new UserEntity(
                2L, "Maria", "Gomez", "456", "+58", "maria@example.com",
                "pass2", LocalDate.of(1995, 5, 5), new RoleEntity(2L, "EMPLEADO", "Descripción del empleado")
        );
    }

    @Test
    @DisplayName("Debería ser igual si los IDs son iguales")
    void equals_ShouldReturnTrueIfIdsAreEqual() {
        assertEquals(user1, user2);
    }

    @Test
    @DisplayName("Debería ser diferente si los IDs son diferentes")
    void equals_ShouldReturnFalseIfIdsAreDifferent() {
        assertNotEquals(user1, user3);
    }

    @Test
    @DisplayName("Debería tener el mismo hashCode si los IDs son iguales")
    void hashCode_ShouldReturnSameHashCodeIfIdsAreEqual() {
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    @DisplayName("Debería tener diferente hashCode si los IDs son diferentes")
    void hashCode_ShouldReturnDifferentHashCodeIfIdsAreDifferent() {
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    @DisplayName("Debería crear un UserEntity con constructor vacío")
    void constructor_ShouldCreateEmptyUserEntity() {
        UserEntity emptyUser = new UserEntity();
        assertNotNull(emptyUser);
    }

    @Test
    @DisplayName("Debería crear un UserEntity con todos los campos")
    void constructor_ShouldCreateUserEntityWithAllFields() {
        assertEquals(1L, user1.getId());
        assertEquals("Juan", user1.getName());
        assertEquals("Perez", user1.getLastName());
        assertEquals("123", user1.getIdentityDocument());
        assertEquals("+57", user1.getPhoneNumber());
        assertEquals("juan@example.com", user1.getEmail());
        assertEquals("pass", user1.getPassword());
        assertEquals(LocalDate.of(1990, 1, 1), user1.getBirthDate());
        assertEquals(role, user1.getRole());
    }

    @Test
    @DisplayName("Debería permitir establecer y obtener campos")
    void settersAndGetters_ShouldWorkCorrectly() {
        UserEntity newUser = new UserEntity();
        newUser.setId(10L);
        newUser.setName("TestName");
        newUser.setLastName("TestLastName");
        newUser.setIdentityDocument("doc123");
        newUser.setPhoneNumber("phone+123");
        newUser.setEmail("test@test.com");
        newUser.setPassword("newPass");
        newUser.setBirthDate(LocalDate.of(2000,1,1));
        newUser.setRole(new RoleEntity(3L, "CLIENTE", "Cliente"));

        assertEquals(10L, newUser.getId());
        assertEquals("TestName", newUser.getName());
        assertEquals("TestLastName", newUser.getLastName());
        assertEquals("doc123", newUser.getIdentityDocument());
        assertEquals("phone+123", newUser.getPhoneNumber());
        assertEquals("test@test.com", newUser.getEmail());
        assertEquals("newPass", newUser.getPassword());
        assertEquals(LocalDate.of(2000,1,1), newUser.getBirthDate());
        assertNotNull(newUser.getRole());
        assertEquals("CLIENTE", newUser.getRole().getName());
    }
}
