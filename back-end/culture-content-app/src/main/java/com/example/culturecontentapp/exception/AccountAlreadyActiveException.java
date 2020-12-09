package com.example.culturecontentapp.exception;

public class AccountAlreadyActiveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountAlreadyActiveException(String message) {
        super(message);
    }
}
