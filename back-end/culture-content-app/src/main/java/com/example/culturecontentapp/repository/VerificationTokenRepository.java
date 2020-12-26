package com.example.culturecontentapp.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    List<VerificationToken> deleteByAccount(Account account);
}
