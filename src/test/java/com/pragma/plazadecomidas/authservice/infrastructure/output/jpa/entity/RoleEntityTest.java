package com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity;

import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.entity.RoleEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoleEntityTest {

    private RoleEntity createDefaultRoleEntity() {
        return new RoleEntity(1L, "ADMIN", "Administrator Role Description");
    }

    @Test
    @DisplayName("Test full constructor and getters")
    void testFullConstructorAndGetters() {
        Long id = 1L;
        String name = "EMPLOYEE";
        String description = "Employee Role Description";

        RoleEntity entity = new RoleEntity(id, name, description);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
    }

    @Test
    @DisplayName("Test default constructor and setters")
    void testDefaultConstructorAndSetters() {
        RoleEntity entity = new RoleEntity();
        Long id = 2L;
        String name = "CLIENT";
        String description = "Client Role Description";

        entity.setId(id);
        entity.setName(name);
        entity.setDescription(description);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
    }

    @Test
    @DisplayName("equals(): Should return true for same object instance")
    void equals_SameInstance_ReturnsTrue() {
        RoleEntity entity = createDefaultRoleEntity();
        assertTrue(entity.equals(entity));
    }

    @Test
    @DisplayName("equals(): Should return true for two identical objects")
    void equals_IdenticalObjects_ReturnsTrue() {
        RoleEntity entity1 = createDefaultRoleEntity();
        RoleEntity entity2 = createDefaultRoleEntity(); // Lombok's @Data means all fields are considered for equality
        assertTrue(entity1.equals(entity2));
        assertTrue(entity2.equals(entity1)); // Symmetry
    }

    @Test
    @DisplayName("equals(): Should return false for null object")
    void equals_NullObject_ReturnsFalse() {
        RoleEntity entity = createDefaultRoleEntity();
        assertFalse(entity.equals(null));
    }

    @Test
    @DisplayName("equals(): Should return false for object of different class")
    void equals_DifferentClass_ReturnsFalse() {
        RoleEntity entity = createDefaultRoleEntity();
        Object differentObject = new Object(); // An instance of a different class
        assertFalse(entity.equals(differentObject));
    }

    @Test
    @DisplayName("equals(): Should return false when ID is different")
    void equals_DifferentId_ReturnsFalse() {
        RoleEntity entity1 = createDefaultRoleEntity();
        RoleEntity entity2 = createDefaultRoleEntity();
        entity2.setId(99L); // Only ID is different
        assertFalse(entity1.equals(entity2));
    }

    @Test
    @DisplayName("equals(): Should return false when name is different")
    void equals_DifferentName_ReturnsFalse() {
        RoleEntity entity1 = createDefaultRoleEntity();
        RoleEntity entity2 = createDefaultRoleEntity();
        entity2.setName("DIFFERENT_NAME"); // Only name is different
        assertFalse(entity1.equals(entity2));
    }

    @Test
    @DisplayName("equals(): Should return false when description is different")
    void equals_DifferentDescription_ReturnsFalse() {
        RoleEntity entity1 = createDefaultRoleEntity();
        RoleEntity entity2 = createDefaultRoleEntity();
        entity2.setDescription("Different description here."); // Only description is different
        assertFalse(entity1.equals(entity2));
    }

    @Test
    @DisplayName("hashCode(): Should return same hash code for identical objects")
    void hashCode_IdenticalObjects_ReturnsSameHashCode() {
        RoleEntity entity1 = createDefaultRoleEntity();
        RoleEntity entity2 = createDefaultRoleEntity();
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("hashCode(): Should return different hash code when key fields are different (likely)")
    void hashCode_DifferentKeyFields_ReturnsDifferentHashCode() {
        RoleEntity entity1 = createDefaultRoleEntity();
        RoleEntity entity2 = createDefaultRoleEntity();
        entity2.setId(99L); // ID is a key field
        assertNotEquals(entity1.hashCode(), entity2.hashCode());

        RoleEntity entity3 = createDefaultRoleEntity();
        entity3.setName("SUPER_ADMIN"); // Name is also a key field for hash code
        assertNotEquals(entity1.hashCode(), entity3.hashCode());
    }

    @Test
    @DisplayName("toString(): Should contain all field values")
    void toString_ContainsAllFieldValues() {
        RoleEntity entity = createDefaultRoleEntity();
        String toStringResult = entity.toString();

        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=ADMIN"));
        assertTrue(toStringResult.contains("description=Administrator Role Description"));
    }

    @Test
    @DisplayName("toBuilder(): Should create a builder with current entity's properties")
    void toBuilder_CreatesBuilderWithProperties() {
        RoleEntity originalEntity = createDefaultRoleEntity();
        RoleEntity.RoleEntityBuilder builder = originalEntity.toBuilder();

        assertNotNull(builder); // Ensure builder is created

        RoleEntity builtEntity = builder.build();

        assertEquals(originalEntity.getId(), builtEntity.getId());
        assertEquals(originalEntity.getName(), builtEntity.getName());
        assertEquals(originalEntity.getDescription(), builtEntity.getDescription());
    }

    @Test
    @DisplayName("toBuilder(): Should allow modifying properties and building a new instance")
    void toBuilder_ModifiesAndBuildsNewInstance() {
        RoleEntity originalEntity = createDefaultRoleEntity();
        String newName = "NEW_ROLE";
        String newDescription = "A freshly updated role description.";

        RoleEntity modifiedEntity = originalEntity.toBuilder()
                .name(newName)
                .description(newDescription)
                .build();

        assertEquals(newName, modifiedEntity.getName());
        assertEquals(newDescription, modifiedEntity.getDescription());
        // Verify other properties remain the same as the original
        assertEquals(originalEntity.getId(), modifiedEntity.getId());
    }

    @Test
    @DisplayName("builder(): Should create a new entity from scratch")
    void builder_CreatesNewEntity() {
        Long id = 3L;
        String name = "GUEST";
        String description = "Guest user role.";

        RoleEntity entity = RoleEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
    }

    @Test
    @DisplayName("canEqual(): Should return true for same class instance (Lombok's internal check)")
    void canEqual_SameClassInstance_ReturnsTrue() {
        RoleEntity entity1 = createDefaultRoleEntity();
        RoleEntity entity2 = createDefaultRoleEntity(); // Another instance of the same class
        assertTrue(entity1.equals(entity2));
    }

    @Test
    @DisplayName("canEqual(): Should return false for different class instance (Lombok's internal check)")
    void canEqual_DifferentClassInstance_ReturnsFalse() {
        RoleEntity entity1 = createDefaultRoleEntity();
        Object otherObject = new Object(); // An instance of a different class
        assertFalse(entity1.equals(otherObject));
    }

}
