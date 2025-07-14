package com.pragma.plazadecomidas.authservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoleTest {

    @Test
    @DisplayName("Should create a Role object with all fields correctly initialized by constructor")
    void constructor_ShouldInitializeAllFields() {
        // GIVEN
        Long id = 1L;
        String name = "ADMIN";
        String description = "Administrador del sistema";

        // WHEN
        Role role = new Role(id, name, description);

        // THEN
        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    @DisplayName("Should correctly set and get fields using setters and getters")
    void settersAndGetters_ShouldWorkCorrectly() {
        // GIVEN
        Role role = new Role();
        Long id = 2L;
        String name = "PROPIETARIO";
        String description = "Rol para propietarios de restaurantes";

        // WHEN
        role.setId(id);
        role.setName(name);
        role.setDescription(description);

        // THEN
        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }


    @Test
    @DisplayName("Equals and HashCode should work correctly for Role objects")
    void equalsAndHashCode_ShouldBeConsistent() {
        Role role1 = new Role(1L, "ADMIN", "Administrador");
        Role role2 = new Role(1L, "ADMIN", "Administrador");
        Role role3 = new Role(2L, "PROPIETARIO", "Propietario");


        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());


        assertNotEquals(role1, role3);
        assertNotEquals(role1.hashCode(), role3.hashCode());
    }

}
