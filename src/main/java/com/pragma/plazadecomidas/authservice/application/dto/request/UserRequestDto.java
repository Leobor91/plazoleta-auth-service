package com.pragma.plazadecomidas.authservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(description = "DTO para la creación o actualización de un usuario.")
public class UserRequestDto {

    @JsonProperty("nombre")
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String name;

    @JsonProperty("apellido")
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String lastName;

    @JsonProperty("documento_de_identidad")
    @Schema(description = "Número de documento de identidad del usuario", example = "1234567890")
    private String identityDocument;

    @JsonProperty("celular")
    @Schema(description = "Número de teléfono del usuario", example = "+573101234567")
    private String phoneNumber;

    @JsonProperty("correo")
    @Schema(description = "Correo electrónico del usuario (debe ser único)", example = "juan.perez@example.com")
    private String email;

    @JsonProperty("contraseña")
    @Schema(description = "Contraseña del usuario", example = "MiContraseñaSegura123")
    private String password;

    @JsonProperty("fecha_de_nacimiento")
    @Schema(description = "Fecha de nacimiento del usuario (debe ser una fecha pasada y el usuario debe ser mayor de edad)", example = "2000-01-01")
    private String birthDate;

}
