package com.pragma.plazadecomidas.authservice.domain.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Representación estándar de un mensaje de error de la API.")
public class ExceptionResponse {

    @Schema(description = "Descripción del error.", example = "El Correo ya está registrado || El celular ya está registrado || El documento de identidad ya está registrado")
    @JsonProperty("mensaje")
    private String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

}
