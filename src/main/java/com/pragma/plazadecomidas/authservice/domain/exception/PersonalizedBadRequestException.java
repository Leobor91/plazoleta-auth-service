package com.pragma.plazadecomidas.authservice.domain.exception;

public class PersonalizedBadRequestException extends RuntimeException{

    public PersonalizedBadRequestException(String message) {
        super(message);
    }

}
