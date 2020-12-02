package com.example.culturecontentapp.exception;

public class NewsNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NewsNotFoundException(String message) {
    super(message);
  }
}
