package com.example.culturecontentapp.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @GetMapping("/test")
  public ResponseEntity<Object> test() {
    return new ResponseEntity<>("Success", HttpStatus.OK);
  }
}
