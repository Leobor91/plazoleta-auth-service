package com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoleEntityTest {

    @Test
    @DisplayName("Should create a RoleEntity object with all fields correctly initialized by constructor")
    void constructor_ShouldInitializeAllFields() {
        Long id = 1L;
        String name = "ADMIN";
        String description = "Administrador del sistema en DB";

        RoleEntity roleEntity = new RoleEntity(id, name, description);

        assertEquals(id, roleEntity.getId());
        assertEquals(name, roleEntity.getName());
        assertEquals(description, roleEntity.getDescription());
    }

    @Test
    @DisplayName("Should correctly set and get fields using setters and getters")
    void settersAndGetters_ShouldWorkCorrectly() {
        RoleEntity roleEntity = new RoleEntity();
        Long id = 2L;
        String name = "PROPIETARIO";
        String description = "Rol para propietarios de restaurantes en DB";

        roleEntity.setId(id);
        roleEntity.setName(name);
        roleEntity.setDescription(description);

        assertEquals(id, roleEntity.getId());
        assertEquals(name, roleEntity.getName());
        assertEquals(description, roleEntity.getDescription());
    }

    @Test
    @DisplayName("Equals and HashCode should work correctly for RoleEntity objects")
    void equalsAndHashCode_ShouldBeConsistent() {
        RoleEntity roleEntity1 = new RoleEntity(1L, "ADMIN", "Administrador");
        RoleEntity roleEntity2 = new RoleEntity(1L, "ADMIN", "Administrador");
        RoleEntity roleEntity3 = new RoleEntity(2L, "PROPIETARIO", "Propietario");

        assertEquals(roleEntity1, roleEntity2);
        assertEquals(roleEntity1.hashCode(), roleEntity2.hashCode());

        assertNotEquals(roleEntity1, roleEntity3);
        assertNotEquals(roleEntity1.hashCode(), roleEntity3.hashCode());
    }

    @Test
    @DisplayName("ToString should return expected format for RoleEntity")
    void toString_ShouldReturnExpectedFormat() {
        RoleEntity roleEntity = new RoleEntity(1L, "ADMIN", "Administrador");
        String expectedToString = "RoleEntity(id=1, name=ADMIN, description=Administrador)";
        assertEquals(expectedToString, roleEntity.toString());
    }
}
