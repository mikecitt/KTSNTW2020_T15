package com.example.culturecontentapp.exception;

public class SubTypeNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public SubTypeNotFoundException(String message) {
        super(message);
    }
}
