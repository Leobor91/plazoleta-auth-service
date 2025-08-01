package com.pragma.plazadecomidas.authservice.infrastructure.utils;

public class ValidationConstants {

    // Mensajes de campos obligatorios
    public static final String NAME_REQUIRED_MESSAGE = "El nombre es obligatorio";
    public static final String LAST_NAME_REQUIRED_MESSAGE = "El apellido es obligatorio";
    public static final String DNI_NUMBER_REQUIRED_MESSAGE = "El número de documento de identidad es obligatorio";
    public static final String PHONE_REQUIRED_MESSAGE = "El número de celular es obligatorio";
    public static final String BIRTH_DATE_REQUIRED_MESSAGE = "La fecha de nacimiento es obligatoria";
    public static final String EMAIL_REQUIRED_MESSAGE = "El correo electrónico es obligatorio";
    public static final String PASSWORD_REQUIRED_MESSAGE = "La clave es obligatoria";

    // Mensajes de formato y patrones
    public static final String DNI_NUMBER_FORMAT_MESSAGE = "El documento de identidad debe contener solo números";
    public static final String DNI_NUMBER_REGEX = "^[0-9]+$";

    public static final String PHONE_FORMAT_MESSAGE = "El número de celular no cumple con el formato (+ prefijo y máximo 13 dígitos)";
    public static final String PHONE_REGEX = "^\\+?[0-9]{1,12}$"; // Max 13 chars including '+'

    public static final String EMAIL_FORMAT_MESSAGE = "El formato del correo electrónico es inválido";
    public static final String BIRTH_DATE_PAST_MESSAGE = "La fecha de nacimiento debe ser en el pasado";

    // Constantes para roles y lógica de negocio
    public static final int MIN_AGE_FOR_ADULT = 18;
    public static final String OWNER_ROLE_NAME = "PROPIETARIO"; // Nombre del rol para propietarios

    // --- Mensajes y ejemplos para Swagger/OpenAPI ---

    // Descripciones de campos
    public static final String SCHEMA_NAME_DESCRIPTION = "Nombre del usuario";
    public static final String SCHEMA_LAST_NAME_DESCRIPTION = "Apellido del usuario";
    public static final String SCHEMA_DNI_NUMBER_DESCRIPTION = "Número de documento de identidad (solo números)";
    public static final String SCHEMA_PHONE_DESCRIPTION = "Número de celular con prefijo internacional opcional (máx. 13 caracteres)";
    public static final String SCHEMA_BIRTH_DATE_DESCRIPTION = "Fecha de nacimiento en formato YYYY-MM-DD (debe ser una fecha pasada)";
    public static final String SCHEMA_EMAIL_DESCRIPTION = "Correo electrónico del usuario";
    public static final String SCHEMA_PASSWORD_DESCRIPTION = "Clave de acceso del usuario (será encriptada)";
    public static final String SCHEMA_ID_DESCRIPTION = "Identificador único del usuario";
    public static final String SCHEMA_ROLE_NAME_DESCRIPTION = "Nombre del rol asignado al usuario";

    // Ejemplos de campos
    public static final String SCHEMA_NAME_EXAMPLE = "Juan";
    public static final String SCHEMA_LAST_NAME_EXAMPLE = "Pérez";
    public static final String SCHEMA_DNI_NUMBER_EXAMPLE = "1001234567";
    public static final String SCHEMA_PHONE_EXAMPLE = "+573001234567";
    public static final String SCHEMA_BIRTH_DATE_EXAMPLE = "1985-05-15";
    public static final String SCHEMA_EMAIL_EXAMPLE = "juan.perez@example.com";
    public static final String SCHEMA_PASSWORD_EXAMPLE = "MiClaveSegura123!";
    public static final String SCHEMA_ID_EXAMPLE = "1";
    public static final String SCHEMA_ROLE_NAME_EXAMPLE = "PROPIETARIO";

    // --- Nombres para JsonProperty ---
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "nombre";
    public static final String JSON_PROPERTY_LAST_NAME = "apellido";
    public static final String JSON_PROPERTY_DNI_NUMBER = "numero_documento_identidad";
    public static final String JSON_PROPERTY_PHONE = "celular";
    public static final String JSON_PROPERTY_BIRTH_DATE = "fecha_nacimiento";
    public static final String JSON_PROPERTY_EMAIL = "correo";
    public static final String JSON_PROPERTY_PASSWORD = "clave";
    public static final String JSON_PROPERTY_ROLE_NAME = "nombre_rol";

    private ValidationConstants() {
        // Constructor privado para evitar instanciación
    }

}
