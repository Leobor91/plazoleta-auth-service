package com.pragma.plazadecomidas.authservice.infrastructure.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExceptionResponseTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should create ExceptionResponse with message using constructor")
    void constructor_ShouldSetMessage() {
        // GIVEN
        String expectedMessage = "This is a test message.";

        // WHEN
        ExceptionResponse response = new ExceptionResponse(expectedMessage);

        // THEN
        assertNotNull(response);
        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    @DisplayName("Should set and get message correctly using Lombok @Data")
    void setterAndGetter_ShouldWorkCorrectly() {
        // GIVEN
        ExceptionResponse response = new ExceptionResponse();
        String expectedMessage = "Another test message for setter.";

        // WHEN
        response.setMessage(expectedMessage);

        // THEN
        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    @DisplayName("Should serialize ExceptionResponse to JSON with 'mensaje' property")
    void serialization_ShouldUseJsonProperty() throws Exception {
        // GIVEN
        String originalMessage = "Error occurred due to invalid input.";
        ExceptionResponse response = new ExceptionResponse(originalMessage);

        // WHEN
        String json = objectMapper.writeValueAsString(response);
        System.out.println("Serialized JSON: " + json);

        // THEN
        assertEquals("{\"mensaje\":\"" + originalMessage + "\"}", json);
    }

    @Test
    @DisplayName("Should deserialize JSON with 'mensaje' property to ExceptionResponse")
    void deserialization_ShouldMapJsonProperty() throws Exception {
        // GIVEN
        String json = "{\"mensaje\":\"A message from JSON.\"}";

        // WHEN
        ExceptionResponse response = objectMapper.readValue(json, ExceptionResponse.class);

        // THEN
        assertNotNull(response);
        assertEquals("A message from JSON.", response.getMessage());
    }
}
