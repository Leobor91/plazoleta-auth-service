package com.pragma.plazadecomidas.authservice.infrastructure.input.rest;

import com.pragma.plazadecomidas.authservice.application.dto.request.UserRequestDto;
import com.pragma.plazadecomidas.authservice.application.dto.response.UserResponseDto;
import com.pragma.plazadecomidas.authservice.application.handler.IUserHandler;
import com.pragma.plazadecomidas.authservice.domain.exception.ExceptionResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
                                            value = "{ \"mensaje\": \"El número de documento debe contener solo números\" }"
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
                                            name = "Error de Validación - Contraseña Requerida",
                                            summary = "La contraseña es obligatoria",
                                            value = "{ \"mensaje\": \"La contraseña es obligatoria\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Fecha Nacimiento Requerida",
                                            summary = "La fecha de nacimiento es obligatoria",
                                            value = "{ \"mensaje\": \"La fecha de nacimiento es obligatoria\" }"
                                    )
                            })),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado (ej. Rol no existe)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Rol No Encontrado",
                                            summary = "El rol 'PROPIETARIO' no se encontró en el sistema",
                                            value = "{ \"mensaje\": \"El rol 'PROPIETARIO' no se encontró en el sistema.\" }"
                                    )
                            })),
            @ApiResponse(responseCode = "409", description = "Conflicto con el estado actual del sistema o reglas de negocio",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto de Correo Existente",
                                            summary = "El correo ya está registrado",
                                            value = "{ \"mensaje\": \"El Correo ya está registrado\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto de Celular Existente",
                                            summary = "El número de celular ya está registrado",
                                            value = "{ \"mensaje\": \"El número de celular ya está registrado\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Conflicto de Documento Existente",
                                            summary = "El número de documento de identidad ya está registrado",
                                            value = "{ \"mensaje\": \"El número de documento de identidad ya está registrado\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Fecha de nacimiento futura",
                                            summary = "La fecha de nacimiento debe ser una fecha pasada",
                                            value = "{ \"mensaje\": \"La fecha de nacimiento debe ser una fecha pasada\" }"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Error de Validación - Menor de edad",
                                            summary = "La persona debe ser mayor de edad (18 años)",
                                            value = "{ \"mensaje\": \"La persona debe ser mayor de edad (18 años)\" }"
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
                  "contraseña": "Password123*"
                }
                """
            )
            @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto response = userHandler.saveOwner(userRequestDto);
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
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Rol no encontrado para el usuario",
                                            summary = "El rol asociado al usuario no se encontró.",
                                            value = "{ \"mensaje\": \"El rol 'PROPIETARIO' no se encontró en el sistema.\" }"
                                    )
                            }))
    })
    @GetMapping("/isOwner")
    public ResponseEntity<UserResponseDto> isOwner(
            @Parameter(description = "ID del usuario a verificar",
                    required = true, example = "1")
            @RequestParam Long userId)
    {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userHandler.isOwner(userId));
    }


}
