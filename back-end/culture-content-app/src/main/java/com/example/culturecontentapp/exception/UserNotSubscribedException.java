package com.example.culturecontentapp.exception;

public class UserNotSubscribedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotSubscribedException(String message) {
        super(message);
    }
}
