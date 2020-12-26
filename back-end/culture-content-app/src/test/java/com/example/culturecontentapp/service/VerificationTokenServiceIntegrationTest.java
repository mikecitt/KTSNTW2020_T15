package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.constants.VerificationTokenConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.example.culturecontentapp.exception.AccountAlreadyActiveException;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.repository.AccountRepository;
import com.example.culturecontentapp.repository.VerificationTokenRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class VerificationTokenServiceIntegrationTest {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateVerificationToken() {
        Optional<Account> foundAccount = accountRepository.findByUsername(DB_TOKEN_ACCOUNT_USERNAME);

        verificationTokenService.createVerificationToken(foundAccount.get());

        assertEquals(verificationTokenRepository.count(), DB_TOKEN_COUNT + 1);
    }

    @Test(expected = AccountAlreadyActiveException.class)
    @Transactional
    @Rollback(true)
    public void testCreateVerificationTokenAccountAlreadyActive() {
        Optional<Account> foundAccount = accountRepository.findByUsername(DB_TOKEN_ACTIVE_ACCOUNT_USERNAME);

        verificationTokenService.createVerificationToken(foundAccount.get());
    }
}
