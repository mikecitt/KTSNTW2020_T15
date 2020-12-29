package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.constants.AuthenticationConstants.*;
import static org.junit.Assert.assertEquals;

import com.example.culturecontentapp.exception.AccountAlreadyActiveException;
import com.example.culturecontentapp.exception.AccountAlreadyExistsException;
import com.example.culturecontentapp.exception.AccountNotFoundException;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.AccountRegisterRequest;
import com.example.culturecontentapp.payload.response.AccountRegisterResponse;
import com.example.culturecontentapp.repository.AccountRepository;
import com.example.culturecontentapp.repository.VerificationTokenRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthenticationServiceIntegrationTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void testRegister() {
        AccountRegisterRequest accountRegisterRequest = new AccountRegisterRequest(REGISTER_EMAIL, REGISTER_USERNAME,
                REGISTER_PASSWORD);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<AccountRegisterResponse> response = authenticationService.register(accountRegisterRequest,
                request);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(accountRepository.count(), DB_USERS_COUNT + 1);
        assertEquals(verificationTokenRepository.count(), DB_TOKENS_COUNT + 1);
    }

    @Test(expected = AccountAlreadyExistsException.class)
    @Transactional
    @Rollback(true)
    public void testRegisterEmailAlreadyExist() {
        AccountRegisterRequest accountRegisterRequest = new AccountRegisterRequest(REGISTER_EMAIL_ALREADY_EXIST,
                REGISTER_USERNAME, REGISTER_PASSWORD);

        authenticationService.register(accountRegisterRequest, new MockHttpServletRequest());
    }

    @Test(expected = AccountAlreadyExistsException.class)
    @Transactional
    @Rollback(true)
    public void testRegisterUsernameAlreadyExist() {
        AccountRegisterRequest accountRegisterRequest = new AccountRegisterRequest(REGISTER_EMAIL,
                REGISTER_USERNAME_ALREADY_EXIST, REGISTER_PASSWORD);

        MockHttpServletRequest request = new MockHttpServletRequest();
        authenticationService.register(accountRegisterRequest, request);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testLogin() {

        AccountLoginRequest accountLoginRequest = new AccountLoginRequest(LOGIN_EMAIL, LOGIN_PASSWORD);

        ResponseEntity<?> response = authenticationService.login(accountLoginRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test(expected = BadCredentialsException.class)
    @Transactional
    @Rollback(true)
    public void testLoginBadCredentials() {

        AccountLoginRequest accountLoginRequest = new AccountLoginRequest(LOGIN_EMAIL, LOGIN_BAD_PASSWORD);
        authenticationService.login(accountLoginRequest);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testActivateAccount() {
        ResponseEntity<?> response = authenticationService.activate(DB_TOKEN_VALID);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(verificationTokenRepository.count(), DB_TOKENS_COUNT - 2);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testActivateAccountTokenExpired() {
        ResponseEntity<?> response = authenticationService.activate(DB_TOKEN_INVALID);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testResendActivationEmail() {
        ResponseEntity<?> response = authenticationService.resend(DB_ACTIVATION_EMAIL_VALID,
                new MockHttpServletRequest());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(verificationTokenRepository.count(), DB_TOKENS_COUNT + 1);
    }

    @Test(expected = AccountNotFoundException.class)
    @Transactional
    @Rollback(true)
    public void testResendActivationEmailAccountDoesntExist() {
        authenticationService.resend(DB_ACTIVATION_EMAIL_INVALID, new MockHttpServletRequest());
    }

    @Test(expected = AccountAlreadyActiveException.class)
    @Transactional
    @Rollback(true)
    public void testResendActivationEmailAlreadyActive() {
        authenticationService.resend(DB_ACTIVATION_EMAIL_ALREADY_ACTIVE, new MockHttpServletRequest());
    }
}
