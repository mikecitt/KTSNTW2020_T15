package com.example.culturecontentapp.exception;

// import com.example.culturecontentapp.storage.StorageException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AccountAlreadyExistsException.class)
  protected ResponseEntity<Object> handleAccountAlreadyExistsException(AccountAlreadyExistsException ex,
      WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(AccountNotFoundException.class)
  protected ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NewsNotFoundException.class)
  protected ResponseEntity<Object> handleNewsNotFoundException(NewsNotFoundException ex, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  // @ExceptionHandler(StorageException.class)
  // protected ResponseEntity<Object> handleStorageException(StorageException ex, WebRequest request) {

  //   return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  // }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    return new ResponseEntity<>(ex.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CulturalOfferNotFoundException.class)
  protected ResponseEntity<Object> handleCulturalOfferNotFoundException(CulturalOfferNotFoundException ex, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }
}
