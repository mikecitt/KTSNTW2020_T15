package com.example.culturecontentapp.service;

import java.util.Optional;

import com.example.culturecontentapp.exception.AccountAlreadyExistsException;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.User;
import com.example.culturecontentapp.payload.request.AccountRegisterRequest;
import com.example.culturecontentapp.payload.response.AccountRegisterResponse;
import com.example.culturecontentapp.repository.AccountRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final AccountRepository repository;
  private final ModelMapper modelMapper;

  @Autowired
  public AuthenticationService(AccountRepository repository, ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
  }

  public ResponseEntity<AccountRegisterResponse> register(AccountRegisterRequest request) {
    Optional<Account> entityByEmail = repository.findByEmail(request.getEmail());

    if (entityByEmail.isPresent()) {
      throw new AccountAlreadyExistsException("Account with the given email already exists");
    }

    Optional<Account> entityByUsername = repository.findByUsername(request.getUsername());

    if (entityByUsername.isPresent()) {
      throw new AccountAlreadyExistsException("Account with the given username already exists");
    }

    Account account = modelMapper.map(request, User.class);
    repository.save(account);

    AccountRegisterResponse response = modelMapper.map(account, AccountRegisterResponse.class);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
