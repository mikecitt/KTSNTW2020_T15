package com.example.culturecontentapp.api;

import com.example.culturecontentapp.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  private final AccountService service;

  @Autowired
  public AccountController(AccountService service) {
    this.service = service;
  }
}
