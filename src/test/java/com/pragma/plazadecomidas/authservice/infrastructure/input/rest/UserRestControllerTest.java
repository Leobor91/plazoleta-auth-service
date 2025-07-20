package com.pragma.plazadecomidas.authservice.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.plazadecomidas.authservice.application.dto.request.OwnerRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.handler.IOwnerHandler;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidAgeException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidDniFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidPhoneFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.NotFound;
import com.pragma.plazadecomidas.authservice.domain.exception.UserAlreadyExistsException;
import com.pragma.plazadecomidas.authservice.infrastructure.utils.ValidationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserRestController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IOwnerHandler ownerHandler;

    private OwnerRequestDto ownerRequestDto;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        ownerRequestDto = OwnerRequestDto.builder()
                .name("Juan")
                .lastName("Perez")
                .dniNumber("1234567890")
                .phone("+573001234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("juan.perez@example.com")
                .password("Password123!")
                .build();

        userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name("Juan")
                .lastName("Perez")
                .dniNumber("1234567890")
                .phone("+573001234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("juan.perez@example.com")
                .roleName("ROLE_OWNER")
                .build();
    }

    @Test
    @DisplayName("saveOwner: Should return 201 CREATED for valid owner request")
    void saveOwner_ValidRequest_Returns201Created() throws Exception {
        // GIVEN
        when(ownerHandler.saveOwner(any(OwnerRequestDto.class))).thenReturn(userResponseDto);

        // WHEN & THEN
        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.correo").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.nombre_rol").value(userResponseDto.getRoleName()));

        verify(ownerHandler).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 400 BAD REQUEST if name is blank")
    void saveOwner_BlankName_Returns400BadRequest() throws Exception {
        ownerRequestDto.setName(""); // Set name to blank

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(ValidationConstants.NAME_REQUIRED_MESSAGE));
        verify(ownerHandler, never()).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 400 BAD REQUEST if email format is invalid")
    void saveOwner_InvalidEmailFormat_Returns400BadRequest() throws Exception {
        ownerRequestDto.setEmail("invalid-email"); // Set invalid email format

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(ValidationConstants.EMAIL_FORMAT_MESSAGE));
        verify(ownerHandler, never()).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 400 BAD REQUEST if birthDate is in the future")
    void saveOwner_FutureBirthDate_Returns400BadRequest() throws Exception {
        ownerRequestDto.setBirthDate(LocalDate.now().plusDays(1)); // Set birthDate in the future

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(ValidationConstants.BIRTH_DATE_PAST_MESSAGE));
        verify(ownerHandler, never()).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 400 BAD REQUEST if InvalidAgeException is thrown")
    void saveOwner_InvalidAgeException_Returns400BadRequest() throws Exception {
        doThrow(new InvalidAgeException(ValidationConstants.USER_MUST_BE_ADULT_MESSAGE))
                .when(ownerHandler).saveOwner(any(OwnerRequestDto.class));

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(ValidationConstants.USER_MUST_BE_ADULT_MESSAGE));
        verify(ownerHandler).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 400 BAD REQUEST if InvalidDniFormatException is thrown")
    void saveOwner_InvalidDniFormatException_Returns400BadRequest() throws Exception {
        doThrow(new InvalidDniFormatException(ValidationConstants.DNI_NUMBER_FORMAT_MESSAGE))
                .when(ownerHandler).saveOwner(any(OwnerRequestDto.class));

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(ValidationConstants.DNI_NUMBER_FORMAT_MESSAGE));
        verify(ownerHandler).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 400 BAD REQUEST if InvalidPhoneFormatException is thrown")
    void saveOwner_InvalidPhoneFormatException_Returns400BadRequest() throws Exception {
        doThrow(new InvalidPhoneFormatException(ValidationConstants.PHONE_FORMAT_MESSAGE))
                .when(ownerHandler).saveOwner(any(OwnerRequestDto.class));

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(ValidationConstants.PHONE_FORMAT_MESSAGE));
        verify(ownerHandler).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 409 CONFLICT if UserAlreadyExistsException (email) is thrown")
    void saveOwner_UserAlreadyExistsExceptionEmail_Returns409Conflict() throws Exception {
        String errorMessage = ValidationConstants.EMAIL_ALREADY_EXISTS_MESSAGE + ownerRequestDto.getEmail();
        doThrow(new UserAlreadyExistsException(errorMessage))
                .when(ownerHandler).saveOwner(any(OwnerRequestDto.class));

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value(errorMessage));
        verify(ownerHandler).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 409 CONFLICT if UserAlreadyExistsException (DNI) is thrown")
    void saveOwner_UserAlreadyExistsExceptionDni_Returns409Conflict() throws Exception {
        String errorMessage = ValidationConstants.DNI_ALREADY_EXISTS_MESSAGE + ownerRequestDto.getDniNumber();
        doThrow(new UserAlreadyExistsException(errorMessage))
                .when(ownerHandler).saveOwner(any(OwnerRequestDto.class));

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value(errorMessage));
        verify(ownerHandler).saveOwner(any(OwnerRequestDto.class));
    }

    @Test
    @DisplayName("saveOwner: Should return 409 CONFLICT if UserAlreadyExistsException (phone) is thrown")
    void saveOwner_UserAlreadyExistsExceptionPhone_Returns409Conflict() throws Exception {
        String errorMessage = ValidationConstants.PHONE_ALREADY_EXISTS_MESSAGE + ownerRequestDto.getPhone();
        doThrow(new UserAlreadyExistsException(errorMessage))
                .when(ownerHandler).saveOwner(any(OwnerRequestDto.class));

        mockMvc.perform(post("/api/v1/users/create-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value(errorMessage));
        verify(ownerHandler).saveOwner(any(OwnerRequestDto.class));
    }

    // --- isOwner tests ---

    @Test
    @DisplayName("isOwner: Should return 200 OK for valid userId")
    void isOwner_ValidUserId_Returns200Ok() throws Exception {
        // GIVEN
        Long userId = 1L;
        when(ownerHandler.isOwner(userId)).thenReturn(userResponseDto);

        // WHEN & THEN
        mockMvc.perform(get("/api/v1/users/isOwner")
                        .param("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()) // Assuming ACCEPTED (202) based on your controller
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.correo").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.nombre_rol").value(userResponseDto.getRoleName()));

        verify(ownerHandler).isOwner(userId);
    }

    @Test
    @DisplayName("isOwner: Should return 404 NOT FOUND if NotFound exception is thrown")
    void isOwner_NotFound_Returns404NotFound() throws Exception {
        // GIVEN
        Long userId = 99L;
        doThrow(new NotFound(ValidationConstants.USER_NOT_FOUND_MESSAGE))
                .when(ownerHandler).isOwner(userId);

        // WHEN & THEN
        mockMvc.perform(get("/api/v1/users/isOwner")
                        .param("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value(ValidationConstants.USER_NOT_FOUND_MESSAGE));

        verify(ownerHandler).isOwner(userId);
    }

    @Test
    @DisplayName("isOwner: Should return 400 BAD REQUEST if userId is not provided (missing @RequestParam)")
    void isOwner_MissingUserId_Returns400BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/users/isOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(ownerHandler, never()).isOwner(anyLong());
    }
}


