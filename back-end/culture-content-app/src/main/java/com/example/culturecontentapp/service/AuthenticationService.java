package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.security.jwt.Constants.EXPIRATION_TIME;
import static com.example.culturecontentapp.security.jwt.Constants.PROVIDER;
import static com.example.culturecontentapp.security.jwt.Constants.SECREY_KEY;

import java.util.Date;
import java.util.Optional;

import com.example.culturecontentapp.exception.AccountAlreadyExistsException;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.User;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.AccountRegisterRequest;
import com.example.culturecontentapp.payload.response.AccountRegisterResponse;
import com.example.culturecontentapp.repository.AccountRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService {

  private final AccountRepository repository;
  private final AuthenticationManager authenticationManager;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public AuthenticationService(AccountRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder,
      AuthenticationManager authenticationManager, ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
    this.authenticationManager = authenticationManager;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
    account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
    repository.save(account);

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = Jwts.builder().setIssuer(PROVIDER).setSubject(account.getEmail()).setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SECREY_KEY).compact();

    AccountRegisterResponse response = modelMapper.map(account, AccountRegisterResponse.class);
    response.setToken(token);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  public ResponseEntity<String> login(AccountLoginRequest request) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = Jwts.builder().setIssuer(PROVIDER).setSubject(request.getEmail()).setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SECREY_KEY).compact();

    return new ResponseEntity<>(token, HttpStatus.OK);
  }

}
