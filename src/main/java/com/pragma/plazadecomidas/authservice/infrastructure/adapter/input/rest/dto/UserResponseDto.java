package com.pragma.plazadecomidas.authservice.infrastructure.adapter.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pragma.plazadecomidas.authservice.infrastructure.utils.ValidationConstants;

import java.time.LocalDate;

public class UserResponseDto {

    @JsonProperty(ValidationConstants.JSON_PROPERTY_ID)
    @Schema(description = ValidationConstants.SCHEMA_ID_DESCRIPTION, example = ValidationConstants.SCHEMA_ID_EXAMPLE)
    private Long id;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_NAME)
    @Schema(description = ValidationConstants.SCHEMA_NAME_DESCRIPTION, example = ValidationConstants.SCHEMA_NAME_EXAMPLE)
    private String name;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_LAST_NAME)
    @Schema(description = ValidationConstants.SCHEMA_LAST_NAME_DESCRIPTION, example = ValidationConstants.SCHEMA_LAST_NAME_EXAMPLE)
    private String lastName;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_DNI_NUMBER)
    @Schema(description = ValidationConstants.SCHEMA_DNI_NUMBER_DESCRIPTION, example = ValidationConstants.SCHEMA_DNI_NUMBER_EXAMPLE)
    private String dniNumber;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_PHONE)
    @Schema(description = ValidationConstants.SCHEMA_PHONE_DESCRIPTION, example = ValidationConstants.SCHEMA_PHONE_EXAMPLE)
    private String phone;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_BIRTH_DATE)
    @Schema(description = ValidationConstants.SCHEMA_BIRTH_DATE_DESCRIPTION, example = ValidationConstants.SCHEMA_BIRTH_DATE_EXAMPLE)
    private LocalDate birthDate;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_EMAIL)
    @Schema(description = ValidationConstants.SCHEMA_EMAIL_DESCRIPTION, example = ValidationConstants.SCHEMA_EMAIL_EXAMPLE)
    private String email;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_ROLE_NAME)
    @Schema(description = ValidationConstants.SCHEMA_ROLE_NAME_DESCRIPTION, example = ValidationConstants.SCHEMA_ROLE_NAME_EXAMPLE)
    private String roleName;

}
