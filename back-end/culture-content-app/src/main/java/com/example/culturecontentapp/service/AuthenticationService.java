package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.security.jwt.Constants.EXPIRATION_TIME;
import static com.example.culturecontentapp.security.jwt.Constants.PROVIDER;
import static com.example.culturecontentapp.security.jwt.Constants.SECREY_KEY;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.example.culturecontentapp.event.OnRegistrationCompleteEvent;
import com.example.culturecontentapp.exception.AccountAlreadyActiveException;
import com.example.culturecontentapp.exception.AccountAlreadyExistsException;
import com.example.culturecontentapp.exception.AccountNotFoundException;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.User;
import com.example.culturecontentapp.model.VerificationToken;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.AccountRegisterRequest;
import com.example.culturecontentapp.payload.response.AccountLoginResponse;
import com.example.culturecontentapp.payload.response.AccountRegisterResponse;
import com.example.culturecontentapp.repository.AccountRepository;
import com.example.culturecontentapp.repository.VerificationTokenRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
  private final VerificationTokenRepository verificationTokenRepository;
  ApplicationEventPublisher eventPublisher;

  @Autowired
  public AuthenticationService(AccountRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder,
      AuthenticationManager authenticationManager, ModelMapper modelMapper,
      VerificationTokenRepository verificationTokenRepository, ApplicationEventPublisher eventPublisher) {
    this.repository = repository;
    this.modelMapper = modelMapper;
    this.authenticationManager = authenticationManager;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.verificationTokenRepository = verificationTokenRepository;
    this.eventPublisher = eventPublisher;
  }

  public ResponseEntity<AccountRegisterResponse> register(AccountRegisterRequest userRequest,
      HttpServletRequest request) {
    Optional<Account> entityByEmail = repository.findByEmail(userRequest.getEmail());

    if (entityByEmail.isPresent()) {
      throw new AccountAlreadyExistsException("Account with the given email already exists");
    }

    Optional<Account> entityByUsername = repository.findByUsername(userRequest.getUsername());

    if (entityByUsername.isPresent()) {
      throw new AccountAlreadyExistsException("Account with the given username already exists");
    }

    Account account = modelMapper.map(userRequest, User.class);
    account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
    repository.save(account);

    String appUrl = request.getContextPath();
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(account, request.getLocale(), appUrl));

    AccountRegisterResponse response = modelMapper.map(account, AccountRegisterResponse.class);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  public ResponseEntity<?> resend(String email, HttpServletRequest request) {
    Optional<Account> entityByEmail = repository.findByEmail(email);
    if (!entityByEmail.isPresent()) {
      throw new AccountNotFoundException("Account with the given email doesn't exists");
    }

    if (entityByEmail.get().isActive()) {
      throw new AccountAlreadyActiveException("Account with the given email is already active");
    }

    String appUrl = request.getContextPath();
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(entityByEmail.get(), request.getLocale(), appUrl));

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> activate(String token) {
    Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
    if (!verificationToken.isPresent())
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    Calendar cal = Calendar.getInstance();
    if ((verificationToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    verificationToken.get().getAccount().setActive(true);
    repository.save(verificationToken.get().getAccount());
    verificationTokenRepository.deleteByAccount(verificationToken.get().getAccount());
    // verificationTokenRepository.delete(verificationToken.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<AccountLoginResponse> login(AccountLoginRequest request) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = Jwts.builder().setIssuer(PROVIDER).setSubject(request.getEmail()).setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SECREY_KEY).compact();

    String role = authentication.getAuthorities().iterator().next().getAuthority();
    AccountLoginResponse accountLoginResponse = new AccountLoginResponse(token, role.substring(role.indexOf("_") + 1),
        request.getEmail());

    return new ResponseEntity<>(accountLoginResponse, HttpStatus.OK);
  }

}
