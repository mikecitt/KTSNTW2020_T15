package com.example.culturecontentapp.service;

import java.util.UUID;

import com.example.culturecontentapp.exception.AccountAlreadyActiveException;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.VerificationToken;
import com.example.culturecontentapp.repository.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {
    @Autowired
    private VerificationTokenRepository repository;

    public String createVerificationToken(Account account) {

        if (account.isActive())
            throw new AccountAlreadyActiveException("Given account with is already active");

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, account);
        repository.save(verificationToken);
        return token;
    }
}
