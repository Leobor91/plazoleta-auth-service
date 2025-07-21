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
    public static final String DNI_NUMBER_FORMAT_MESSAGE = "El documento de identidad no debe ser vació y debe contener solo números";
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
    public static final String SCHEMA_BIRTH_DATE_DESCRIPTION = "Fecha de nacimiento en formato YYYY-MM-DD";
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
    public static final String JSON_PROPERTY_DNI_NUMBER = "documento_de_identidad";
    public static final String JSON_PROPERTY_PHONE = "celular";
    public static final String JSON_PROPERTY_BIRTH_DATE = "fecha_de_nacimiento";
    public static final String JSON_PROPERTY_EMAIL = "correo";
    public static final String JSON_PROPERTY_PASSWORD = "clave";
    public static final String JSON_PROPERTY_ROLE_NAME = "rol";

    // --- Mensajes de excepciones de negocio (Agregados aquí) ---
    public static final String USER_MUST_BE_ADULT_MESSAGE = "El usuario debe ser mayor de " + MIN_AGE_FOR_ADULT + " años.";
    public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "Ya existe un usuario con el correo electrónico: ";
    public static final String DNI_ALREADY_EXISTS_MESSAGE = "Ya existe un usuario con el número de documento de identidad: ";
    public static final String ERROR_VALIDATION_MESSAGE = "Error de validación: ";
    public static final String PHONE_ALREADY_EXISTS_MESSAGE = "Ya existe un usuario con el número de celular: ";
    public static final String USER_NOT_FOUND_MESSAGE = "Usuario no encontrado";
    public static final String ROLE_NOT_FOUND_MESSAGE = "El rol 'PROPIETARIO' no se encontró en el sistema.";
    public static final String NOT_SAVED_MESSAGE = "El usuario no pudo ser guardado, verifique los datos ingresados.";
    public static final String  MESSAGE_KEY = "mensaje";
    public static final String ERROR_500_MESSAGE = "Error interno del servidor. Por favor, inténtelo más tarde.";


    private ValidationConstants() {
        // Constructor privado para evitar instanciación
    }

}
