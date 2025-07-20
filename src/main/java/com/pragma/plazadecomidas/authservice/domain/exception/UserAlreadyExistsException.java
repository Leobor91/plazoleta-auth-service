package com.pragma.plazadecomidas.authservice.domain.exception;

public class UserAlreadyExistsException extends DomainException{

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
