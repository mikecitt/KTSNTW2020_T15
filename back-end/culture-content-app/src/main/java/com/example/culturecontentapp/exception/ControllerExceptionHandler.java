package com.example.culturecontentapp.exception;

import com.example.culturecontentapp.storage.StorageException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

  @ExceptionHandler(AccountAlreadyActiveException.class)
  protected ResponseEntity<Object> handleAccountAlreadyActiveException(AccountAlreadyActiveException ex,
      WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(AccountNotFoundException.class)
  protected ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TypeNotFoundException.class)
  protected ResponseEntity<Object> handleTypeNotFoundException(TypeNotFoundException ex, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TypeAlreadyExistsException.class)
  protected ResponseEntity<Object> handleTypeAlreadyExistsException(TypeAlreadyExistsException ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(TypeHasSubTypesException.class)
  protected ResponseEntity<Object> handleTypeHasSubTypeException(TypeHasSubTypesException ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(SubTypeNotFoundException.class)
  protected ResponseEntity<Object> handleSubTypeNotFoundException(SubTypeNotFoundException ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SubTypeHasCulturalOffersException.class)
  protected ResponseEntity<Object> handleSubTypeHasCulturalOffersException(SubTypeHasCulturalOffersException ex,
      WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(SubTypeAlreadyExistsException.class)
  protected ResponseEntity<Object> handleSubTypeAlreadyExistsException(SubTypeAlreadyExistsException ex,
      WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(CulturalOfferAlreadyExistsException.class)
  protected ResponseEntity<Object> handleCulturalOfferAlreadyExistsException(CulturalOfferAlreadyExistsException ex,
      WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(StorageException.class)
  protected ResponseEntity<Object> handleStorageException(StorageException ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NewsNotFoundException.class)
  protected ResponseEntity<Object> handleNewsNotFoundException(NewsNotFoundException ex, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    return new ResponseEntity<>(ex.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CulturalOfferNotFoundException.class)
  protected ResponseEntity<Object> handleCulturalOfferNotFoundException(CulturalOfferNotFoundException ex,
      WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAlreadySubscribedException.class)
  protected ResponseEntity<Object> handleUserAlreadySubscribedException(UserAlreadySubscribedException ex,
      WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(UserNotSubscribedException.class)
  protected ResponseEntity<Object> handleUserNotSubscribedException(UserNotSubscribedException ex, WebRequest request) {

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
