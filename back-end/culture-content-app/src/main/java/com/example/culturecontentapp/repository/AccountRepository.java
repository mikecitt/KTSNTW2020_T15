package com.example.culturecontentapp.repository;

import java.util.Optional;

import com.example.culturecontentapp.model.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);

}
