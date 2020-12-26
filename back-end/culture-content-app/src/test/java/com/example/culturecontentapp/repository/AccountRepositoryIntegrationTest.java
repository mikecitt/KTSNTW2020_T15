package com.example.culturecontentapp.repository;

import java.util.Optional;

import com.example.culturecontentapp.model.Account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.culturecontentapp.constants.AccountConstants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AccountRepositoryIntegrationTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindByEmail() {
        Optional<Account> found = accountRepository.findByEmail(DB_USER_EMAIL);
        assertEquals(DB_USER_USERNAME, found.get().getUsername());
    }

    @Test
    public void testFindByUsername() {
        Optional<Account> found = accountRepository.findByUsername(DB_ADMIN_USERNAME);
        assertEquals(DB_ADMIN_EMAIL, found.get().getEmail());
    }
}
