package com.example.culturecontentapp.service;

import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.VerificationToken;
import com.example.culturecontentapp.repository.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {
    @Autowired
    private VerificationTokenRepository repository;

    public void createVerificationToken(Account account, String token) {
        VerificationToken verificationToken = new VerificationToken(token, account);
        repository.save(verificationToken);
    }
}
