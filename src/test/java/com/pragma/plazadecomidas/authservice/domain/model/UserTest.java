package com.pragma.plazadecomidas.authservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class UserTest {

    @Test
    @DisplayName("Should create a User object with all fields correctly initialized by constructor")
    void constructor_ShouldInitializeAllFields() {
        // GIVEN
        Long id = 1L;
        String name = "Juan";
        String lastName = "Perez";
        String identityDocument = "12345678";
        String phoneNumber = "+573101234567";
        String email = "juan@example.com";
        String password = "hashedPassword123";
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        Role role = new Role(1L, "CLIENTE", "Rol de cliente");

        // WHEN
        User user = new User(
                id,
                name,
                lastName,
                identityDocument,
                phoneNumber,
                email,
                password,
                birthDate,
                String.valueOf(role.getId()),
                role.getName());

        // THEN
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(identityDocument, user.getIdentityDocument());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(role.getId(), Long.parseLong(user.getRoleId()));
        assertNotNull(user.getRoleId());
        assertEquals("CLIENTE", user.getRoleName());
    }

    @Test
    @DisplayName("Should correctly set and get fields using setters and getters")
    void settersAndGetters_ShouldWorkCorrectly() {
        // GIVEN
        User user = new User(); // Usar constructor sin argumentos para probar setters
        Long id = 2L;
        String name = "Maria";
        String lastName = "Gomez";
        String documentNumber = "87654321";
        String phone = "+573209876543";
        String email = "maria@example.com";
        String password = "anotherHashedPassword";
        LocalDate birthDate = LocalDate.of(1985, 10, 20);
        Role newRole = new Role(2L, "PROPIETARIO", "Rol de propietario");

        // WHEN
        user.setId(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setIdentityDocument(documentNumber);
        user.setPhoneNumber(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(birthDate);
        user.setRoleId(String.valueOf(newRole.getId()));

        // THEN
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(documentNumber, user.getIdentityDocument());
        assertEquals(phone, user.getPhoneNumber());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(newRole.getId(), Long.parseLong(user.getRoleId()));
    }

    // Si implementas equals y hashCode manualmente, testéalos aquí
    @Test
    @DisplayName("Equals and HashCode should work correctly for User objects")
    void equalsAndHashCode_ShouldBeConsistent() {
        Role role1 = new Role(1L, "CLIENTE", "Rol de cliente");
        Role role2 = new Role(1L, "CLIENTE", "Rol de cliente");

        User user1 = new User(
                1L,
                "Juan",
                "Perez",
                "12345678",
                "+573100000000",
                "juan@example.com",
                "pass",
                LocalDate.of(1990,1,1),
                String.valueOf(role1.getId()),
                role1.getName());
        User user2 = new User(1L,
                "Juan",
                "Perez",
                "12345678",
                "+573100000000",
                "juan@example.com",
                "pass",
                LocalDate.of(1990,1,1),
                String.valueOf(role2.getId()),
                role2.getName());
        User user3 = new User(
                2L,
                "Pedro",
                "Gomez",
                "87654321",
                "+57320000000",
                "pedro@example.com",
                "pass2",
                LocalDate.of(1991,2,2),
                String.valueOf(role1.getId()),
                role1.getName());


        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());


        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }


    @Test
    @DisplayName("ToString should return expected format")
    void toString_ShouldReturnExpectedFormat() {
        Role role = new Role(1L, "CLIENTE", "Rol de cliente");
        User user = new User(1L,
                "Juan",
                "Perez",
                "12345678",
                "+573100000000",
                "juan@example.com",
                "pass",
                LocalDate.of(1990,1,1),
                String.valueOf(role.getId()),
                role.getName());

        String expectedToString = "User(id=1, name=Juan, lastName=Perez, identityDocument=12345678, phoneNumber=+573100000000, email=juan@example.com, password=pass, birthDate=1990-01-01, roleId=1, roleName=CLIENTE)";
        assertEquals(expectedToString, user.toString());
    }

}
