package com.example.culturecontentapp.exception;

public class CulturalOfferNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CulturalOfferNotFoundException(String message) {
    super(message);
  }
}
