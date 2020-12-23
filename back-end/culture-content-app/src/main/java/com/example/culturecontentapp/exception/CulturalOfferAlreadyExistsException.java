package com.example.culturecontentapp.exception;

public class CulturalOfferAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CulturalOfferAlreadyExistsException(String message) {
    super(message);
  }
}
