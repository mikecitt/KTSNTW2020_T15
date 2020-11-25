package com.example.culturecontentapp.api;

import com.example.culturecontentapp.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationService service;

  @Autowired
  public AuthenticationController(AuthenticationService service) {
    this.service = service;
  }

  @GetMapping("/test")
  public ResponseEntity<Object> test() {
    return new ResponseEntity<>("Success", HttpStatus.OK);
  }
}
