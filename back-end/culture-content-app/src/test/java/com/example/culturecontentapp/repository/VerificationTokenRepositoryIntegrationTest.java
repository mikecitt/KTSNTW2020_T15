package com.example.culturecontentapp.repository;

import static com.example.culturecontentapp.constants.VerificationTokenConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.VerificationToken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class VerificationTokenRepositoryIntegrationTest {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindByToken() {

        Optional<VerificationToken> found = verificationTokenRepository
                .findByToken("da9110a4-985d-4ce2-b514-e0abf7272eb8");

        assertTrue(found.isPresent());
        assertEquals(DB_TOKEN_EXPIRY_DATE, found.get().getExpiryDate().toString());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteByAccount() {

        Optional<Account> foundAccount = accountRepository.findByUsername(DB_TOKEN_ACCOUNT_USERNAME);
        List<VerificationToken> deleted = verificationTokenRepository.deleteByAccount(foundAccount.get());

        assertEquals(TOKEN_DELETED_COUNT, deleted.size());
    }
}
