package com.pragma.plazadecomidas.authservice.infrastructure.exception;

import com.pragma.plazadecomidas.authservice.domain.exception.DomainException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidAgeException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidDniFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidPhoneFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.NotFound;
import com.pragma.plazadecomidas.authservice.domain.exception.UserAlreadyExistsException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "Error de validación en la solicitud.";
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null) {
            errorMessage = fieldError.getDefaultMessage();
        } else if (ex.getBindingResult().hasGlobalErrors()) {
            errorMessage = ex.getBindingResult().getGlobalError().getDefaultMessage();
        }
        ExceptionResponse response = new ExceptionResponse(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionResponse> handleDomainException(DomainException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        if (ex instanceof NotFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else if (ex instanceof InvalidAgeException
                || ex instanceof InvalidDniFormatException
                || ex instanceof InvalidPhoneFormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if( ex instanceof UserAlreadyExistsException){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ExceptionResponse response = new ExceptionResponse("Solicitud con JSON mal formado o datos ilegibles.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ExceptionResponse> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        ExceptionResponse response = new ExceptionResponse("Tipo de dato inesperado en la solicitud.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Cambiado para devolver ExceptionResponse y eliminar @ResponseStatus redundante
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(String.format("Parámetro '%s' es requerido", ex.getParameterName()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejador genérico para cualquier otra excepción no capturada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse("Error interno del servidor inesperado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
