package com.pragma.plazadecomidas.authservice.infrastructure.exception;

import com.pragma.plazadecomidas.authservice.domain.exception.DomainException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidAgeException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidDniFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidPhoneFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.NotFound;
import com.pragma.plazadecomidas.authservice.domain.exception.UserAlreadyExistsException;
import com.pragma.plazadecomidas.authservice.infrastructure.utils.ValidationConstants;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(ValidationConstants.MESSAGE_KEY, error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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
        ExceptionResponse response = new ExceptionResponse(ValidationConstants.SCHEMA_BIRTH_DATE_DESCRIPTION);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ExceptionResponse> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        ExceptionResponse response = new ExceptionResponse(ValidationConstants.SCHEMA_BIRTH_DATE_DESCRIPTION);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
