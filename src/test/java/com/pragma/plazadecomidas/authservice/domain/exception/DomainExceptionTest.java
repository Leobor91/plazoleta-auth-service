package com.pragma.plazadecomidas.authservice.domain.exception;


import com.pragma.plazadecomidas.authservice.domain.model.MessageEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DomainExceptionTest {

    @Test
    @DisplayName("PersonalizedException: Debería crearse con un mensaje")
    void personalizedException_ShouldBeCreatedWithMessage() {
        String message = "Este es un mensaje de excepción personalizada.";
        PersonalizedException exception = new PersonalizedException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("PersonalizedException: Debería crearse con un mensaje y una causa")
    void personalizedException_ShouldBeCreatedWithMessageAndCause() {
        String message = "Excepción con causa.";
        Throwable cause = new RuntimeException("Causa original");
        PersonalizedException exception = new PersonalizedException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("PersonalizedException: Debería crearse con el mensaje por defecto 'Usuario no encontrado'")
    void PersonalizedException_ShouldBeCreatedWithDefaultMessage() {
        PersonalizedException exception = new PersonalizedException(MessageEnum.USER_NOT_FOUND.getMessage());

        assertNotNull(exception);
        assertEquals("Usuario no encontrado", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("PersonalizedException: Debería crearse con un mensaje personalizado")
    void PersonalizedException_ShouldBeCreatedWithCustomMessage() {
        String message = "El usuario con ID 123 no existe.";
        PersonalizedException exception = new PersonalizedException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("PersonalizedException: Debería crearse con un mensaje y una causa")
    void PersonalizedException_ShouldBeCreatedWithMessageAndCause() {
        String message = "Error al buscar usuario.";
        Throwable cause = new IllegalArgumentException("ID inválido");
        PersonalizedException exception = new PersonalizedException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
