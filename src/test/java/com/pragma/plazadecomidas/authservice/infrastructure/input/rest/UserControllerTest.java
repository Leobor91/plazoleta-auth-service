package com.pragma.plazadecomidas.authservice.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.handler.IUserHandler;
import com.pragma.plazadecomidas.authservice.config.TestSecurityConfig;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = {TestSecurityConfig.class})
@WebMvcTest(UserController.class)
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserHandler userHandler;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        userRequestDto = new UserRequestDto(
                "Nuevo",
                "Propietario",
                "1234567890",
                "+573001234567",
                "nuevo.propietario@example.com",
                "PasswordSegura123*",
                "1990, 5, 15"
        );

        userResponseDto = new UserResponseDto(
                1L,
                "Nuevo",
                "Propietario",
                "nuevo.propietario@example.com",
                "",
                "",
                LocalDate.now(),
                ""

        );
    }

    @Test
    @WithMockUser(roles = "ADMINISTRADOR")
    @DisplayName("Should create a new owner and return 201 Created")
    void saveOwner_ShouldReturn201CreatedAndUserResponseDto() throws Exception {
        // GIVEN
        when(userHandler.saveOwner(any(UserRequestDto.class))).thenReturn(userResponseDto);

        // WHEN & THEN
        mockMvc.perform(post("/api/v1/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.nombre").value(userResponseDto.getName()))
                .andExpect(jsonPath("$.apellido").value(userResponseDto.getLastName()))
                .andExpect(jsonPath("$.documento_de_identidad").value(userResponseDto.getIdentityDocument()))
                .andExpect(jsonPath("$.celular").value(userResponseDto.getPhoneNumber()))
                .andExpect(jsonPath("$.fecha_de_nacimiento").value(userResponseDto.getBirthDate().toString()))
                .andExpect(jsonPath("$.correo").value(userResponseDto.getEmail()))
        .andExpect(jsonPath("$.rol").value(userResponseDto.getRole()));

    }

    @Test
    @DisplayName("Should return 409 Conflict if handler throws PersonalizedException for saveOwner")
    void saveOwner_ShouldReturn400BadRequest_WhenValidationFails() throws Exception {
        // GIVEN
        UserRequestDto invalidDto = new UserRequestDto(
                "",
                "Apellido",
                "1234567890",
                "+573001234567",
                "email@example.com",
                "password",
                "1990, 1, 1"
        );

        when(userHandler.saveOwner(any(UserRequestDto.class)))
                .thenThrow(new PersonalizedException("El Nombre es obligatorio"));

        // WHEN & THEN
        mockMvc.perform(post("/api/v1/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Should return 409 Conflict if handler throws PersonalizedException (e.g., email exists)")

    void saveOwner_ShouldReturn409Conflict_WhenIllegalArgumentException() throws Exception {
        // GIVEN
        when(userHandler.saveOwner(any(UserRequestDto.class)))
                .thenThrow(new PersonalizedException("El email ya está registrado"));

        // WHEN & THEN
        mockMvc.perform(post("/api/v1/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isConflict());
    }

}
