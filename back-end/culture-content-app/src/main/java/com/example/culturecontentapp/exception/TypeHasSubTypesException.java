package com.example.culturecontentapp.exception;

public class TypeHasSubTypesException extends RuntimeException {
   
    private static final long serialVersionUID = 1L;

    public TypeHasSubTypesException(String message) {
        super(message);
    }
}
