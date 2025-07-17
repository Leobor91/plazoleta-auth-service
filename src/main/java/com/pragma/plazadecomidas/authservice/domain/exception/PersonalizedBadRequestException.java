package com.pragma.plazadecomidas.authservice.domain.exception;

public class PersonalizedBadRequestException extends RuntimeException{

    public PersonalizedBadRequestException(String message) {
        super(message);
    }

    public PersonalizedBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonalizedBadRequestException() {
        super();
    }

    public PersonalizedBadRequestException(Throwable cause) {
        super(cause);
    }

}
