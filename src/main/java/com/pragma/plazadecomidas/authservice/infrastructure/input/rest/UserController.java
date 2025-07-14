package com.pragma.plazadecomidas.authservice.infrastructure.input.rest;

import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Gestión de Usuarios", description = "Operaciones relacionadas con la creación y administración de usuarios.")
public class UserController {

    private final IUserHandler userHandler;

    @Operation(summary = "Registrar un nuevo propietario",
            description = "Permite registrar un nuevo usuario con el rol de PROPIETARIO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Propietario creado con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto en la creación de usuario",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto de Correo",
                                            summary = "El Correo ya está registrado",
                                            value = "{ \"message\": \"El Correo ya está registrado\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto del Celular",
                                            summary = "El número de celular ya está registrado",
                                            value = "{ \"message\": \"El número de celular ya está registrado\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto del Documento de Identidad",
                                            summary = "El número de documento de identiodad ya está registrado",
                                            value = "{ \"message\": \"El número de documento de identiodad ya está registrado\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Rol No Encontrado",
                                            summary = "El rol 'PROPIETARIO' no se encontró en el sistema",
                                            value = "{ \"message\": \"El rol 'PROPIETARIO' no se encontró en el sistema.\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Nombre Requerido",
                                            summary = "El nombre es obligatorio",
                                            value = "{ \"message\": \"El nombre es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Apellido Requerido",
                                            summary = "El apellido es obligatorio",
                                            value = "{ \"message\": \"El apellido es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Número de documento Requerido",
                                            summary = "El número de documento es obligatorio",
                                            value = "{ \"message\": \"El número de documento es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Formato del número de documento",
                                            summary = "El número de documento debe contener solo números",
                                            value = "{ \"message\": \"El número de documento debe contener solo números\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Número de celular Requerido",
                                            summary = "El número de celular es obligatorio",
                                            value = "{ \"message\": \"El número de celular es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Correo Requerido",
                                            summary = "El Correo es obligatorio",
                                            value = "{ \"message\": \"El Correo es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Formato Correo",
                                            summary = "El Correo debe tener un formato válido",
                                            value = "{ \"message\": \"El Correo debe tener un formato válido\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Contraseña Requerida",
                                            summary = "La contraseña es obligatoria",
                                            value = "{ \"message\": \"La contraseña es obligatoria\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Fecha de nacimiento Requerida",
                                            summary = "La fecha de nacimiento es obligatoria",
                                            value = "{ \"message\": \"La fecha de nacimiento es obligatoria\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Formato fecha de nacimiento",
                                            summary = "La fecha de nacimiento debe tener el formato 'YYYY-MM-DD",
                                            value = "{ \"message\": \"La fecha de nacimiento debe tener el formato 'YYYY-MM-DD\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Rango de fecha de nacimiento",
                                            summary = "La persona debe ser mayor de edad (18 años)",
                                            value = "{ \"message\": \"La persona debe ser mayor de edad (18 años)\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Rango de fecha de nacimiento",
                                            summary = "La fecha de nacimiento debe ser una fecha pasada",
                                            value = "{ \"message\": \"La fecha de nacimiento debe ser una fecha pasada\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Formato del número de celular",
                                            summary = "El número de celular debe tener mas de 11  dígitos y debe iniciar con '+'",
                                            value = "{ \"message\": \"El número de celular debe tener mas de 11  dígitos y debe iniciar con '+'\" }"
                                    )
                            }))
    })
    @PostMapping("/owner")
    public ResponseEntity<UserResponseDto> saveOwner(
            @Parameter(description = "Request con datos de usuario", required = true, example =
                    "{\n" +
                    "  \"nombre\": \"Juan\",\n" +
                    "  \"apellido\": \"Pérez\",\n" +
                    "  \"correo\": \"juan.perez@correo.com\",\n" +
                    "  \"celular\": \"+5755300123720\",\n" +
                    "  \"documento_de_identidad\": \"126794800\",\n" +
                    "  \"fecha_de_nacimiento\": \"2010-05-03\",\n" +
                    "  \"contraseña\": \"Password123*\"\n" +
                    "}")
            @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto response = userHandler.saveOwner(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
