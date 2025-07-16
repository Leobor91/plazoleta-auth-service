package com.pragma.plazadecomidas.authservice.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonalizedExceptionTest {


    @Test
    @DisplayName("Should create exception with no message")
    void constructor_ShouldCreateWithNoMessage() {
        PersonalizedException exception = new PersonalizedException();
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should create exception with provided message")
    void constructor_ShouldCreateWithMessage() {
        String expectedMessage = "User not found with the given ID.";
        PersonalizedException exception = new PersonalizedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should create exception with provided message and cause")
    void constructor_ShouldCreateWithMessageAndCause() {
        String expectedMessage = "An error occurred during user retrieval.";
        Throwable cause = new RuntimeException("Original database error.");
        PersonalizedException exception = new PersonalizedException(expectedMessage, cause);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with provided cause")
    void constructor_ShouldCreateWithCause() {
        Throwable cause = new RuntimeException("Underlying service unavailable.");
        PersonalizedException exception = new PersonalizedException(cause);
        assertEquals(cause, exception.getCause());
        assertNotNull(exception.getMessage());
    }
}
