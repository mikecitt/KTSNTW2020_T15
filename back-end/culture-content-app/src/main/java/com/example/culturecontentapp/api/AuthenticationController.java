package com.example.culturecontentapp.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.AccountRegisterRequest;
import com.example.culturecontentapp.payload.response.AccountRegisterResponse;
import com.example.culturecontentapp.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private final AuthenticationService service;

  @Autowired
  public AuthenticationController(AuthenticationService service) {
    this.service = service;
  }

  @PostMapping("/register")
  public ResponseEntity<AccountRegisterResponse> register(@RequestBody @Valid AccountRegisterRequest userRequest,
      HttpServletRequest request) {
    return service.register(userRequest, request);
  }

  @GetMapping("/resend")
  public ResponseEntity<?> resend(@RequestParam("email") String email, HttpServletRequest request) {
    return service.resend(email, request);
  }

  @GetMapping("/activate")
  public ResponseEntity<?> activate(@RequestParam("token") String token) {
    return service.activate(token);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody @Valid AccountLoginRequest request) {
    return service.login(request);
  }
}
