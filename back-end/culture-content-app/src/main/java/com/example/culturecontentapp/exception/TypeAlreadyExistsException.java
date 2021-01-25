package com.example.culturecontentapp.exception;

public class TypeAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TypeAlreadyExistsException(String message) {
        super(message);
    }
}
