package com.example.culturecontentapp.service;

import com.example.culturecontentapp.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final AccountRepository repository;

  @Autowired
  public AuthenticationService(AccountRepository repository) {
    this.repository = repository;
  }
}
