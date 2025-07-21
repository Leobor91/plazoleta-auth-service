package com.pragma.plazadecomidas.authservice.domain.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    private Role defaultRole;

    @BeforeEach
    void setUp() {
        defaultRole = Role.builder()
                .id(1L)
                .name("ADMIN")
                .description("Administrator role")
                .build();
    }


    private User createDefaultUser() {
        return User.builder()
                .id(1L)
                .name("Juan")
                .lastName("Perez")
                .dniNumber("1234567890")
                .phone("+573101234567")
                .birthDate(LocalDate.of(1990, 5, 15))
                .email("juan.perez@example.com")
                .password("Password123!")
                .role(defaultRole)
                .build();
    }

    @Test
    @DisplayName("Test full constructor and getters")
    void testFullConstructorAndGetters() {
        Long id = 1L;
        String name = "Ana";
        String lastName = "Gomez";
        String dniNumber = "9876543210";
        String phone = "+573209876543";
        LocalDate birthDate = LocalDate.of(1988, 11, 22);
        String email = "ana.gomez@example.com";
        String password = "SecurePassword!";
        Role role = Role.builder().id(2L).name("EMPLOYEE").description("Employee Role").build();

        User user = new User(id, name, lastName, dniNumber, phone, birthDate, email, password, role);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(dniNumber, user.getDniNumber());
        assertEquals(phone, user.getPhone());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    @DisplayName("Test default constructor and setters")
    void testDefaultConstructorAndSetters() {
        User user = new User();
        Long id = 2L;
        String name = "Pedro";
        String lastName = "Lopez";
        String dniNumber = "1122334455";
        String phone = "+573151122334";
        LocalDate birthDate = LocalDate.of(1995, 1, 1);
        String email = "pedro.lopez@example.com";
        String password = "NewPassword!";
        Role role = Role.builder().id(3L).name("OWNER").description("Owner Role").build();

        user.setId(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setDniNumber(dniNumber);
        user.setPhone(phone);
        user.setBirthDate(birthDate);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(dniNumber, user.getDniNumber());
        assertEquals(phone, user.getPhone());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    @DisplayName("equals(): Should return true for same object instance")
    void equals_SameInstance_ReturnsTrue() {
        User user = createDefaultUser();
        assertTrue(user.equals(user));
    }

    @Test
    @DisplayName("equals(): Should return true for two identical objects")
    void equals_IdenticalObjects_ReturnsTrue() {
        User user1 = createDefaultUser();
        User user2 = createDefaultUser();
        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1)); // Symmetry
    }

    @Test
    @DisplayName("equals(): Should return false for null object")
    void equals_NullObject_ReturnsFalse() {
        User user = createDefaultUser();
        assertFalse(user.equals(null));
    }

    @Test
    @DisplayName("equals(): Should return false for object of different class")
    void equals_DifferentClass_ReturnsFalse() {
        User user = createDefaultUser();
        Object differentObject = new Object();
        assertFalse(user.equals(differentObject));
    }

    @Test
    @DisplayName("equals(): Should return false when DNI number is different")
    void equals_DifferentDniNumber_ReturnsFalse() {
        User user1 = createDefaultUser();
        User user2 = createDefaultUser();
        user2.setDniNumber("0000000000");
        assertFalse(user1.equals(user2));
    }

    @Test
    @DisplayName("equals(): Should return false when email is different")
    void equals_DifferentEmail_ReturnsFalse() {
        User user1 = createDefaultUser();
        User user2 = createDefaultUser();
        user2.setEmail("different.email@example.com");
        assertFalse(user1.equals(user2));
    }

    @Test
    @DisplayName("equals(): Should return false when role is different")
    void equals_DifferentRole_ReturnsFalse() {
        User user1 = createDefaultUser();
        User user2 = createDefaultUser();
        Role differentRole = Role.builder().id(99L).name("FAKE_ROLE").description("Fake role").build();
        user2.setRole(differentRole);
        assertFalse(user1.equals(user2));
    }

    @Test
    @DisplayName("hashCode(): Should return same hash code for identical objects")
    void hashCode_IdenticalObjects_ReturnsSameHashCode() {
        User user1 = createDefaultUser();
        User user2 = createDefaultUser();
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    @DisplayName("hashCode(): Should return different hash code when key fields are different (likely)")
    void hashCode_DifferentKeyFields_ReturnsDifferentHashCode() {
        User user1 = createDefaultUser();
        User user2 = createDefaultUser();
        user2.setId(99L);
        assertNotEquals(user1.hashCode(), user2.hashCode());

        User user3 = createDefaultUser();
        user3.setDniNumber("9999999999");
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    @DisplayName("toString(): Should contain all field values")
    void toString_ContainsAllFieldValues() {
        User user = createDefaultUser();
        String toStringResult = user.toString();

        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=Juan"));
        assertTrue(toStringResult.contains("lastName=Perez"));
        assertTrue(toStringResult.contains("dniNumber=1234567890"));
        assertTrue(toStringResult.contains("phone=+573101234567"));
        assertTrue(toStringResult.contains("birthDate=1990-05-15"));
        assertTrue(toStringResult.contains("email=juan.perez@example.com"));
        assertTrue(toStringResult.contains("role=Role(id=1, name=ADMIN, description=Administrator role)"));
    }

    @Test
    @DisplayName("builder(): Should create a new User from scratch using the builder")
    void builder_CreatesNewUser() {
        User user = User.builder()
                .id(3L)
                .name("BuilderUser")
                .lastName("BuilderLastName")
                .dniNumber("5000123456")
                .phone("+573005554433")
                .birthDate(LocalDate.of(1998, 7, 20))
                .email("builder.user@example.com")
                .password("BuilderPass#1")
                .role(Role.builder().id(4L).name("CLIENT").description("Client role").build())
                .build();

        assertNotNull(user);
        assertEquals("BuilderUser", user.getName());
        assertEquals("BuilderLastName", user.getLastName());
        assertEquals("5000123456", user.getDniNumber());
        assertEquals("+573005554433", user.getPhone());
        assertEquals(LocalDate.of(1998, 7, 20), user.getBirthDate());
        assertEquals("builder.user@example.com", user.getEmail());
        assertEquals("BuilderPass#1", user.getPassword());
        assertEquals("CLIENT", user.getRole().getName());
    }

    @Test
    @DisplayName("toBuilder(): Should create a builder with current User's properties and allow modifications")
    void toBuilder_CreatesBuilderWithPropertiesAndAllowsModification() {
        User originalUser = createDefaultUser();
        String newName = "UpdatedUser";
        String newEmail = "updated.user@example.com";
        Role newRole = Role.builder().id(5L).name("NEW_ROLE").description("New role").build();

        User modifiedUser = originalUser.toBuilder()
                .name(newName)
                .email(newEmail)
                .role(newRole)
                .build();

        assertNotNull(modifiedUser);
        assertEquals(newName, modifiedUser.getName());
        assertEquals(newEmail, modifiedUser.getEmail());
        assertEquals(newRole, modifiedUser.getRole());
        assertEquals(originalUser.getLastName(), modifiedUser.getLastName());
    }

    @Test
    @DisplayName("canEqual(): Should return true for same class instance")
    void canEqual_SameClassInstance_ReturnsTrue() {
        User user1 = createDefaultUser();
        User user2 = createDefaultUser();
        assertTrue(user1.canEqual(user2));
    }

    @Test
    @DisplayName("canEqual(): Should return false for different class instance")
    void canEqual_DifferentClassInstance_ReturnsFalse() {
        User user1 = createDefaultUser();
        Object otherObject = new Object();
        assertFalse(user1.canEqual(otherObject));
    }



    @Test
    @DisplayName("isAdult(): Should return true if user is 18 or older")
    void isAdult_UserIsAdult_ReturnsTrue() {
        LocalDate birthDate1 = LocalDate.now().minusYears(18);
        LocalDate birthDate2 = LocalDate.now().minusYears(25);
        LocalDate birthDate3 = LocalDate.now().minusYears(18).minusDays(1);

        User adultUser1 = User.builder().birthDate(birthDate1).build();
        User adultUser2 = User.builder().birthDate(birthDate2).build();
        User adultUser3 = User.builder().birthDate(birthDate3).build();

        assertTrue(adultUser1.isAdult());
        assertTrue(adultUser2.isAdult());
        assertTrue(adultUser3.isAdult());
    }

    @Test
    @DisplayName("isAdult(): Should return false if user is less than 18 years old")
    void isAdult_UserIsUnderage_ReturnsFalse() {
        LocalDate birthDate1 = LocalDate.now().minusYears(17).minusMonths(6);
        LocalDate birthDate2 = LocalDate.now().minusYears(18).plusDays(1);

        User underageUser1 = User.builder().birthDate(birthDate1).build();
        User underageUser2 = User.builder().birthDate(birthDate2).build();

        assertFalse(underageUser1.isAdult());
        assertFalse(underageUser2.isAdult());
    }

    @Test
    @DisplayName("isAdult(): Should return false if birthDate is null")
    void isAdult_BirthDateIsNull_ReturnsFalse() {
        User userWithNullBirthDate = User.builder().birthDate(null).build();
        assertFalse(userWithNullBirthDate.isAdult());
    }

}
