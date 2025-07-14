package com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleEntityTest {

    private RoleEntity role1;
    private RoleEntity role2;
    private RoleEntity role3;

    @BeforeEach
    void setUp() {
        role1 = new RoleEntity(1L, "PROPIETARIO", "Rol para propietarios");
        role2 = new RoleEntity(1L, "PROPIETARIO", "Rol para propietarios");
        role3 = new RoleEntity(2L, "EMPLEADO", "Rol para empleados");
    }

    @Test
    @DisplayName("Debería ser igual si los IDs son iguales")
    void equals_ShouldReturnTrueIfIdsAreEqual() {
        assertEquals(role1, role2);
    }

    @Test
    @DisplayName("Debería ser diferente si los IDs son diferentes")
    void equals_ShouldReturnFalseIfIdsAreDifferent() {
        assertNotEquals(role1, role3);
    }

    @Test
    @DisplayName("Debería tener el mismo hashCode si los IDs son iguales")
    void hashCode_ShouldReturnSameHashCodeIfIdsAreEqual() {
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    @DisplayName("Debería tener diferente hashCode si los IDs son diferentes")
    void hashCode_ShouldReturnDifferentHashCodeIfIdsAreDifferent() {
        assertNotEquals(role1.hashCode(), role3.hashCode());
    }

    @Test
    @DisplayName("Debería crear un RoleEntity con constructor vacío")
    void constructor_ShouldCreateEmptyRoleEntity() {
        RoleEntity emptyRole = new RoleEntity();
        assertNotNull(emptyRole);
    }

    @Test
    @DisplayName("Debería crear un RoleEntity con todos los campos")
    void constructor_ShouldCreateRoleEntityWithAllFields() {
        assertEquals(1L, role1.getId());
        assertEquals("PROPIETARIO", role1.getName());
        assertEquals("Rol para propietarios", role1.getDescription());
    }

    @Test
    @DisplayName("Debería permitir establecer y obtener campos")
    void settersAndGetters_ShouldWorkCorrectly() {
        RoleEntity newRole = new RoleEntity();
        newRole.setId(5L);
        newRole.setName("ADMIN");
        newRole.setDescription("Administrador del sistema");

        assertEquals(5L, newRole.getId());
        assertEquals("ADMIN", newRole.getName());
        assertEquals("Administrador del sistema", newRole.getDescription());
    }
}
