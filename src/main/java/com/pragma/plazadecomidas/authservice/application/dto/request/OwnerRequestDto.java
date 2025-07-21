package com.pragma.plazadecomidas.authservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pragma.plazadecomidas.authservice.infrastructure.utils.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OwnerRequestDto {


    @JsonProperty(ValidationConstants.JSON_PROPERTY_NAME)
    @Schema(description = ValidationConstants.SCHEMA_NAME_DESCRIPTION, example = ValidationConstants.SCHEMA_NAME_EXAMPLE)
    @NotBlank(message = ValidationConstants.NAME_REQUIRED_MESSAGE)
    private String name;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_LAST_NAME)
    @Schema(description = ValidationConstants.SCHEMA_LAST_NAME_DESCRIPTION, example = ValidationConstants.SCHEMA_LAST_NAME_EXAMPLE)
    @NotBlank(message = ValidationConstants.LAST_NAME_REQUIRED_MESSAGE)
    private String lastName;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_DNI_NUMBER)
    @Schema(description = ValidationConstants.SCHEMA_DNI_NUMBER_DESCRIPTION, example = ValidationConstants.SCHEMA_DNI_NUMBER_EXAMPLE)
    @NotBlank(message = ValidationConstants.DNI_NUMBER_REQUIRED_MESSAGE)
    @Pattern(regexp = ValidationConstants.DNI_NUMBER_REGEX, message = ValidationConstants.DNI_NUMBER_FORMAT_MESSAGE)
    private String dniNumber;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_PHONE)
    @Schema(description = ValidationConstants.SCHEMA_PHONE_DESCRIPTION, example = ValidationConstants.SCHEMA_PHONE_EXAMPLE)
    @NotBlank(message = ValidationConstants.PHONE_REQUIRED_MESSAGE)
    @Pattern(regexp = ValidationConstants.PHONE_REGEX, message = ValidationConstants.PHONE_FORMAT_MESSAGE)
    private String phone;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_BIRTH_DATE)
    @Schema(description = ValidationConstants.SCHEMA_BIRTH_DATE_DESCRIPTION, example = ValidationConstants.SCHEMA_BIRTH_DATE_EXAMPLE)
    @NotNull(message = ValidationConstants.BIRTH_DATE_REQUIRED_MESSAGE)
    @Past(message = ValidationConstants.BIRTH_DATE_PAST_MESSAGE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_EMAIL)
    @Schema(description = ValidationConstants.SCHEMA_EMAIL_DESCRIPTION, example = ValidationConstants.SCHEMA_EMAIL_EXAMPLE)
    @NotBlank(message = ValidationConstants.EMAIL_REQUIRED_MESSAGE)
    @Email(message = ValidationConstants.EMAIL_FORMAT_MESSAGE)
    private String email;

    @JsonProperty(ValidationConstants.JSON_PROPERTY_PASSWORD)
    @Schema(description = ValidationConstants.SCHEMA_PASSWORD_DESCRIPTION, example = ValidationConstants.SCHEMA_PASSWORD_EXAMPLE)
    @NotBlank(message = ValidationConstants.PASSWORD_REQUIRED_MESSAGE)
    private String password;

}
