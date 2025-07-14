package com.pragma.plazadecomidas.authservice.domain.model;

public enum MessageEnum {

    USER_REQUEST_NULL("El UserRequestDto no puede ser nulo"),
    EMAIL_ALREADY_EXISTS("El Correo ya está registrado"),
    PHONE_ALREADY_EXISTS("El número de celular ya está registrado"),
    DOCUMENT_ALREADY_EXISTS("El número de documento de identiodad ya está registrado"),
    ROLE_NOT_FOUND("El rol 'PROPIETARIO' no se encontró en el sistema."),
    PROPIETARIO("PROPIETARIO"),
    NAME_REQUIRED("El nombre es obligatorio"),
    LASTNAME_REQUIRED("El apellido es obligatorio"),
    DOCUMENT_REQUIRED("El número de documento es obligatorio"),
    DOCUMENT_FORMAT("El número de documento debe contener solo números"),
    PHONE_REQUIRED("El número de celular es obligatorio"),
    PHONE_FORMAT("El número de celular debe tener mas de 11  dígitos y debe iniciar con '+'"),
    PHONE_STRUCTURE("^\\+[0-9]{11,14}$"),
    EMAIL_REQUIRED("El Correo es obligatorio"),
    EMAIL_FORMAT("El Correo debe tener un formato válido"),
    PASSWORD_REQUIRED("La contraseña es obligatoria"),
    BIRTHDATE_REQUIRED("La fecha de nacimiento es obligatoria"),
    BIRTHDATE_PAST("La fecha de nacimiento debe ser una fecha pasada"),
    EMAIL_STRUCTURE("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"),
    BIRTHDATE_ADULT("La persona debe ser mayor de edad (18 años)"),
    BIRTHDATE_FORMAT("La fecha de nacimiento debe tener el formato 'YYYY-MM-DD'"),
    EIGHTEENNUMBER("18"),
    NUMBER_FORMAT("^\\d+$");


    private final String message;

    MessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
