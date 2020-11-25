package com.example.culturecontentapp.service;

import com.example.culturecontentapp.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final AccountRepository repository;

  @Autowired
  public AccountService(AccountRepository repository) {
    this.repository = repository;
  }
}
