package com.pragma.plazadecomidas.authservice.infrastructure.input.rest;

import com.pragma.plazadecomidas.authservice.application.dto.request.OwnerRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.handler.IOwnerHandler;
import com.pragma.plazadecomidas.authservice.infrastructure.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Gestión de Usuarios", description = "Operaciones relacionadas con la creación y administración de usuarios propietarios de restaurantes.")

public class UserRestController {

    private final IOwnerHandler ownerHandler;

    @Operation(summary = "Crear un nuevo propietario de restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida o datos faltantes/incorrectos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Nombre Requerido",
                                            summary = "El nombre es obligatorio",
                                            value = "{ \"mensaje\": \"El nombre es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Apellido Requerido",
                                            summary = "El apellido es obligatorio",
                                            value = "{ \"mensaje\": \"El apellido es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Documento Requerido",
                                            summary = "El número de documento es obligatorio",
                                            value = "{ \"mensaje\": \"El número de documento es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Formato Documento",
                                            summary = "El número de documento debe contener solo números",
                                            value = "{ \"mensaje\": \"El documento de identidad no debe ser vació y debe contener solo números\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Celular Requerido",
                                            summary = "El número de celular es obligatorio",
                                            value = "{ \"mensaje\": \"El número de celular es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Formato Celular",
                                            summary = "El número de celular debe ser únicamente numérico y puede iniciar con '+'. Por ejemplo: +573005698325.",
                                            value = "{ \"mensaje\": \"El número de celular debe ser únicamente numérico y puede iniciar con '+'. Por ejemplo: +573005698325.\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Correo Requerido",
                                            summary = "El correo es obligatorio",
                                            value = "{ \"mensaje\": \"El correo es obligatorio\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Formato Correo",
                                            summary = "El correo debe tener un formato válido",
                                            value = "{ \"mensaje\": \"El correo debe tener un formato válido\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - clave Requerida",
                                            summary = "La clave es obligatoria",
                                            value = "{ \"mensaje\": \"La clave es obligatoria\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Fecha Nacimiento Requerida",
                                            summary = "La fecha de nacimiento es obligatoria",
                                            value = "{ \"mensaje\": \"La fecha de nacimiento es obligatoria\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Formato fecha de nacimiento",
                                            summary = "Formato fecha de nacimiento",
                                            value = "{ \"mensaje\": \"Fecha de nacimiento en formato YYYY-MM-DD\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Fecha de nacimiento pasada",
                                            summary = "Fecha de nacimiento pasada",
                                            value = "{ \"mensaje\": \"La fecha de nacimiento debe ser en el pasado\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Ser mayor de edad",
                                            summary = "Ser mayor de edad",
                                            value = "{ \"mensaje\": \"El usuario debe ser mayor de 18 años.\" }"
                                    )
                            })),
            @ApiResponse(responseCode = "409", description = "Conflicto con el estado actual del sistema o reglas de negocio",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto de Correo Existente",
                                            summary = "El correo ya está registrado",
                                            value = "{ \"mensaje\": \"Ya existe un usuario con el correo electrónico: juan.perez@example.com\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto de Celular Existente",
                                            summary = "El número de celular ya está registrado",
                                            value = "{ \"mensaje\": \"Ya existe un usuario con el número de celular: +573001234567\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto de Documento Existente",
                                            summary = "El número de documento de identidad ya está registrado",
                                            value = "{ \"mensaje\": \"Ya existe un usuario con el número de documento de identidad: 123467890\" }"
                                    )
                            }))
    })
    @PostMapping("/create-owner")
    public ResponseEntity<UserResponseDto> saveOwner(
            @Parameter(description = "Request con datos de usuario",
                    required = true,
                    example = """
                {
                  "nombre": "Juan",
                  "apellido": "Pérez",
                  "correo": "juan.perez@correo.com",
                  "celular": "+5755300123720",
                  "documento_de_identidad": "126794800",
                  "fecha_de_nacimiento": "2010-05-03",
                  "clave": "Password123*"
                }
                """
            )
            @Valid @RequestBody OwnerRequestDto userRequestDto) {
        UserResponseDto response = ownerHandler.saveOwner(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Verificar si un usuario es propietario por su ID",
            description = "Consulta el servicio de autenticación para determinar si el  usuario  existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa: Devuelve un usuario.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuario o rol no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Usuario no encontrado",
                                            summary = "Usuario no encontrado",
                                            value = "{ \"mensaje\": \"Usuario no encontrado\" }"
                                    )
                            }))
    })
    @GetMapping("/isOwner")
    public ResponseEntity<UserResponseDto> isOwner(
            @Parameter(description = "ID del usuario a verificar",
                    required = true, example = "1")
            @RequestParam Long userId)
    {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ownerHandler.isOwner(userId));
    }
}
