package com.example.culturecontentapp.exception;

public class UserAlreadySubscribedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserAlreadySubscribedException(String message) {
        super(message);
    }
}
