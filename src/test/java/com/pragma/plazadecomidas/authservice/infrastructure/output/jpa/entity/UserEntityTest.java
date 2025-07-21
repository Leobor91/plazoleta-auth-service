package com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity;

import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserEntityTest {

    private RoleEntity defaultRole;

    @BeforeEach
    void setUp() {

        defaultRole = new RoleEntity(1L, "ADMIN", "Administrator Role");
    }


    private UserEntity createDefaultUserEntity() {
        return new UserEntity(
                1L,
                "Juan",
                "Perez",
                "1234567890",
                "+573101234567",
                LocalDate.of(1990, 5, 15),
                "juan.perez@example.com",
                "password123",
                defaultRole
        );
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
        String password = "securepassword";
        RoleEntity role = new RoleEntity(2L, "EMPLOYEE", "Employee Role");

        UserEntity entity = new UserEntity(id, name, lastName, dniNumber, phone, birthDate, email, password, role);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(lastName, entity.getLastName());
        assertEquals(dniNumber, entity.getDniNumber());
        assertEquals(phone, entity.getPhone());
        assertEquals(birthDate, entity.getBirthDate());
        assertEquals(email, entity.getEmail());
        assertEquals(password, entity.getPassword());
        assertEquals(role, entity.getRole());
    }

    @Test
    @DisplayName("Test default constructor and setters")
    void testDefaultConstructorAndSetters() {
        UserEntity entity = new UserEntity();
        Long id = 2L;
        String name = "Pedro";
        String lastName = "Lopez";
        String dniNumber = "1122334455";
        String phone = "+573151122334";
        LocalDate birthDate = LocalDate.of(1995, 1, 1);
        String email = "pedro.lopez@example.com";
        String password = "newpassword";
        RoleEntity role = new RoleEntity(3L, "OWNER", "Owner Role");

        entity.setId(id);
        entity.setName(name);
        entity.setLastName(lastName);
        entity.setDniNumber(dniNumber);
        entity.setPhone(phone);
        entity.setBirthDate(birthDate);
        entity.setEmail(email);
        entity.setPassword(password);
        entity.setRole(role);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(lastName, entity.getLastName());
        assertEquals(dniNumber, entity.getDniNumber());
        assertEquals(phone, entity.getPhone());
        assertEquals(birthDate, entity.getBirthDate());
        assertEquals(email, entity.getEmail());
        assertEquals(password, entity.getPassword());
        assertEquals(role, entity.getRole());
    }

    @Test
    @DisplayName("equals(): Should return true for same object instance")
    void equals_SameInstance_ReturnsTrue() {
        UserEntity entity = createDefaultUserEntity();
        assertTrue(entity.equals(entity));
    }

    @Test
    @DisplayName("equals(): Should return true for two identical objects")
    void equals_IdenticalObjects_ReturnsTrue() {
        UserEntity entity1 = createDefaultUserEntity();
        UserEntity entity2 = createDefaultUserEntity();
        assertTrue(entity1.equals(entity2));
        assertTrue(entity2.equals(entity1));
    }

    @Test
    @DisplayName("equals(): Should return false for null object")
    void equals_NullObject_ReturnsFalse() {
        UserEntity entity = createDefaultUserEntity();
        assertFalse(entity.equals(null));
    }

    @Test
    @DisplayName("equals(): Should return false for object of different class")
    void equals_DifferentClass_ReturnsFalse() {
        UserEntity entity = createDefaultUserEntity();
        Object differentObject = new Object();
        assertFalse(entity.equals(differentObject));
    }

    @Test
    @DisplayName("equals(): Should return false when DNI number is different")
    void equals_DifferentDniNumber_ReturnsFalse() {
        UserEntity entity1 = createDefaultUserEntity();
        UserEntity entity2 = createDefaultUserEntity();
        entity2.setDniNumber("0000000000");
        assertFalse(entity1.equals(entity2));
    }

    @Test
    @DisplayName("equals(): Should return false when email is different")
    void equals_DifferentEmail_ReturnsFalse() {
        UserEntity entity1 = createDefaultUserEntity();
        UserEntity entity2 = createDefaultUserEntity();
        entity2.setEmail("different.email@example.com");
        assertFalse(entity1.equals(entity2));
    }

    @Test
    @DisplayName("equals(): Should return false when role is different")
    void equals_DifferentRole_ReturnsFalse() {
        UserEntity entity1 = createDefaultUserEntity();
        UserEntity entity2 = createDefaultUserEntity();
        RoleEntity differentRole = new RoleEntity(99L, "NEW_ROLE", "New Role Desc");
        entity2.setRole(differentRole);
        assertFalse(entity1.equals(entity2));
    }

    @Test
    @DisplayName("hashCode(): Should return same hash code for identical objects")
    void hashCode_IdenticalObjects_ReturnsSameHashCode() {
        UserEntity entity1 = createDefaultUserEntity();
        UserEntity entity2 = createDefaultUserEntity();
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("hashCode(): Should return different hash code when key fields are different (likely)")
    void hashCode_DifferentKeyFields_ReturnsDifferentHashCode() {
        UserEntity entity1 = createDefaultUserEntity();
        UserEntity entity2 = createDefaultUserEntity();
        entity2.setId(99L);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());

        UserEntity entity3 = createDefaultUserEntity();
        entity3.setDniNumber("9999999999");
        assertNotEquals(entity1.hashCode(), entity3.hashCode());
    }

    @Test
    @DisplayName("toString(): Should contain all field values")
    void toString_ContainsAllFieldValues() {
        UserEntity entity = createDefaultUserEntity();
        String toStringResult = entity.toString();

        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=Juan"));
        assertTrue(toStringResult.contains("lastName=Perez"));
        assertTrue(toStringResult.contains("dniNumber=1234567890"));
        assertTrue(toStringResult.contains("phone=+573101234567"));
        assertTrue(toStringResult.contains("birthDate=1990-05-15"));
        assertTrue(toStringResult.contains("email=juan.perez@example.com"));
        assertTrue(toStringResult.contains("role=RoleEntity(id=1, name=ADMIN, description=Administrator Role)"));
    }


    @Test
    @DisplayName("toBuilder(): Should create a builder with current entity's properties")
    void toBuilder_CreatesBuilderWithProperties() {
        UserEntity originalEntity = createDefaultUserEntity();
        UserEntity.UserEntityBuilder builder = originalEntity.toBuilder();

        assertNotNull(builder);

        UserEntity builtEntity = builder.build();

        assertEquals(originalEntity.getId(), builtEntity.getId());
        assertEquals(originalEntity.getName(), builtEntity.getName());
        assertEquals(originalEntity.getEmail(), builtEntity.getEmail());
        assertEquals(originalEntity.getBirthDate(), builtEntity.getBirthDate());
        assertEquals(originalEntity.getRole(), builtEntity.getRole());

    }

    @Test
    @DisplayName("toBuilder(): Should allow modifying properties and building a new instance")
    void toBuilder_ModifiesAndBuildsNewInstance() {
        UserEntity originalEntity = createDefaultUserEntity();
        String newName = "Nuevo Nombre";
        String newEmail = "nuevo.email@example.com";
        LocalDate newBirthDate = LocalDate.of(2000, 1, 1);

        UserEntity modifiedEntity = originalEntity.toBuilder()
                .name(newName)
                .email(newEmail)
                .birthDate(newBirthDate)
                .build();

        assertEquals(newName, modifiedEntity.getName());
        assertEquals(newEmail, modifiedEntity.getEmail());
        assertEquals(newBirthDate, modifiedEntity.getBirthDate());
        assertEquals(originalEntity.getId(), modifiedEntity.getId());
        assertEquals(originalEntity.getLastName(), modifiedEntity.getLastName());
    }

    @Test
    @DisplayName("builder(): Should create a new entity from scratch")
    void builder_CreatesNewEntity() {
        Long id = 3L;
        String name = "Carlos";
        String lastName = "Ruiz";
        String dniNumber = "5544332211";
        String phone = "+573059988776";
        LocalDate birthDate = LocalDate.of(1975, 12, 25);
        String email = "carlos.ruiz@example.com";
        String password = "anotherpassword";
        RoleEntity role = new RoleEntity(4L, "CLIENT", "Client Role");

        UserEntity entity = UserEntity.builder()
                .id(id)
                .name(name)
                .lastName(lastName)
                .dniNumber(dniNumber)
                .phone(phone)
                .birthDate(birthDate)
                .email(email)
                .password(password)
                .role(role)
                .build();

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(lastName, entity.getLastName());
        assertEquals(dniNumber, entity.getDniNumber());
        assertEquals(phone, entity.getPhone());
        assertEquals(birthDate, entity.getBirthDate());
        assertEquals(email, entity.getEmail());
        assertEquals(password, entity.getPassword());
        assertEquals(role, entity.getRole());
    }


    @Test
    @DisplayName("canEqual(): Should return true for same class instance (Lombok's internal check)")
    void canEqual_SameClassInstance_ReturnsTrue() {
        UserEntity entity1 = createDefaultUserEntity();
        UserEntity entity2 = createDefaultUserEntity();
        assertTrue(entity1.equals(entity2));
    }

    @Test
    @DisplayName("canEqual(): Should return false for different class instance (Lombok's internal check)")
    void canEqual_DifferentClassInstance_ReturnsFalse() {
        UserEntity entity1 = createDefaultUserEntity();
        Object otherObject = new Object();
        assertFalse(entity1.equals(otherObject));
    }
}
