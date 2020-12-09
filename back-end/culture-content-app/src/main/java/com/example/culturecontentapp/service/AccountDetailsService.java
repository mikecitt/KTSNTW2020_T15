package com.example.culturecontentapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.culturecontentapp.exception.AccountNotFoundException;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {

  private AccountRepository repository;

  @Autowired
  public AccountDetailsService(AccountRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {

    Optional<Account> entity = repository.findByEmail(username);
    if (!entity.isPresent()) {
      throw new AccountNotFoundException("Account with the given username not found");
    }

    Account account = entity.get();

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + (account instanceof Account ? "ADMIN" : "USER")));

    return new User(account.getEmail(), account.getPassword(), account.isActive(), true, true, true, authorities);
  }
}
