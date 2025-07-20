package com.pragma.plazadecomidas.authservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoleTest {

    private Role createDefaultRole() {
        return Role.builder()
                .id(1L)
                .name("ADMIN")
                .description("Administrator Role Description")
                .build();
    }

    @Test
    @DisplayName("Test full constructor and getters")
    void testFullConstructorAndGetters() {
        Long id = 1L;
        String name = "EMPLOYEE";
        String description = "Employee Role Description";

        Role role = new Role(id, name, description);

        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    @DisplayName("Test default constructor and setters")
    void testDefaultConstructorAndSetters() {
        Role role = new Role();
        Long id = 2L;
        String name = "CLIENT";
        String description = "Client Role Description";

        role.setId(id);
        role.setName(name);
        role.setDescription(description);

        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    @DisplayName("equals(): Should return true for same object instance")
    void equals_SameInstance_ReturnsTrue() {
        Role role = createDefaultRole();
        assertTrue(role.equals(role));
    }

    @Test
    @DisplayName("equals(): Should return true for two identical objects")
    void equals_IdenticalObjects_ReturnsTrue() {
        Role role1 = createDefaultRole();
        Role role2 = createDefaultRole();
        assertTrue(role1.equals(role2));
        assertTrue(role2.equals(role1));
    }

    @Test
    @DisplayName("equals(): Should return false for null object")
    void equals_NullObject_ReturnsFalse() {
        Role role = createDefaultRole();
        assertFalse(role.equals(null));
    }

    @Test
    @DisplayName("equals(): Should return false for object of different class")
    void equals_DifferentClass_ReturnsFalse() {
        Role role = createDefaultRole();
        Object differentObject = new Object();
        assertFalse(role.equals(differentObject));
    }

    @Test
    @DisplayName("equals(): Should return false when ID is different")
    void equals_DifferentId_ReturnsFalse() {
        Role role1 = createDefaultRole();
        Role role2 = createDefaultRole();
        role2.setId(99L); // Only ID is different
        assertFalse(role1.equals(role2));
    }

    @Test
    @DisplayName("equals(): Should return false when name is different")
    void equals_DifferentName_ReturnsFalse() {
        Role role1 = createDefaultRole();
        Role role2 = createDefaultRole();
        role2.setName("DIFFERENT_NAME");
        assertFalse(role1.equals(role2));
    }

    @Test
    @DisplayName("equals(): Should return false when description is different")
    void equals_DifferentDescription_ReturnsFalse() {
        Role role1 = createDefaultRole();
        Role role2 = createDefaultRole();
        role2.setDescription("Different description here.");
        assertFalse(role1.equals(role2));
    }

    @Test
    @DisplayName("hashCode(): Should return same hash code for identical objects")
    void hashCode_IdenticalObjects_ReturnsSameHashCode() {
        Role role1 = createDefaultRole();
        Role role2 = createDefaultRole();
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    @DisplayName("hashCode(): Should return different hash code when key fields are different (likely)")
    void hashCode_DifferentKeyFields_ReturnsDifferentHashCode() {
        Role role1 = createDefaultRole();
        Role role2 = createDefaultRole();
        role2.setId(99L);
        assertNotEquals(role1.hashCode(), role2.hashCode());

        Role role3 = createDefaultRole();
        role3.setName("SUPER_ADMIN");
        assertNotEquals(role1.hashCode(), role3.hashCode());
    }

    @Test
    @DisplayName("toString(): Should contain all field values")
    void toString_ContainsAllFieldValues() {
        Role role = createDefaultRole();
        String toStringResult = role.toString();

        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=ADMIN"));
        assertTrue(toStringResult.contains("description=Administrator Role Description"));
    }

    @Test
    @DisplayName("toBuilder(): Should create a builder with current role's properties")
    void toBuilder_CreatesBuilderWithProperties() {
        Role originalRole = createDefaultRole();
        Role.RoleBuilder builder = originalRole.toBuilder();

        assertNotNull(builder);

        Role builtRole = builder.build();

        assertEquals(originalRole.getId(), builtRole.getId());
        assertEquals(originalRole.getName(), builtRole.getName());
        assertEquals(originalRole.getDescription(), builtRole.getDescription());
    }

    @Test
    @DisplayName("toBuilder(): Should allow modifying properties and building a new instance")
    void toBuilder_ModifiesAndBuildsNewInstance() {
        Role originalRole = createDefaultRole();
        String newName = "NEW_ROLE";
        String newDescription = "A freshly updated role description.";

        Role modifiedRole = originalRole.toBuilder()
                .name(newName)
                .description(newDescription)
                .build();

        assertEquals(newName, modifiedRole.getName());
        assertEquals(newDescription, modifiedRole.getDescription());
        assertEquals(originalRole.getId(), modifiedRole.getId());
    }

    @Test
    @DisplayName("builder(): Should create a new role from scratch")
    void builder_CreatesNewRole() {
        Long id = 3L;
        String name = "GUEST";
        String description = "Guest user role.";

        Role role = Role.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();

        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    @DisplayName("canEqual(): Should return true for same class instance (Lombok's internal check)")
    void canEqual_SameClassInstance_ReturnsTrue() {
        Role role1 = createDefaultRole();
        Role role2 = createDefaultRole(); // Another instance of the same class
        assertTrue(role1.canEqual(role2));
    }

    @Test
    @DisplayName("canEqual(): Should return false for different class instance (Lombok's internal check)")
    void canEqual_DifferentClassInstance_ReturnsFalse() {
        Role role1 = createDefaultRole();
        Object otherObject = new Object(); // An instance of a different class
        assertFalse(role1.canEqual(otherObject));
    }

}
