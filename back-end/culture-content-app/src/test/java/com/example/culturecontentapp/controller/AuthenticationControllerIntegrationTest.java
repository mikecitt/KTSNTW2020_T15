package com.example.culturecontentapp.controller;

import static com.example.culturecontentapp.constants.AuthenticationConstants.*;
import static org.junit.Assert.assertEquals;

import com.example.culturecontentapp.payload.request.AccountRegisterRequest;
import com.example.culturecontentapp.payload.response.AccountRegisterResponse;
import com.example.culturecontentapp.repository.AccountRepository;
import com.example.culturecontentapp.repository.VerificationTokenRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void testRegister() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(
                new AccountRegisterRequest(REGISTER_EMAIL, REGISTER_USERNAME, REGISTER_PASSWORD), headers);

        ResponseEntity<AccountRegisterResponse> response = restTemplate.exchange("/api/auth/register", HttpMethod.POST,
                httpEntity, new ParameterizedTypeReference<>() {
                });

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(accountRepository.count(), DB_USERS_COUNT + 1);
        assertEquals(verificationTokenRepository.count(), DB_TOKENS_COUNT + 1);
    }

    /*
     * @Test(expected = AccountAlreadyExistsException.class)
     * 
     * @Transactional
     * 
     * @Rollback(true) public void testRegisterEmailAlreadyExist() {
     * 
     * HttpHeaders headers = new HttpHeaders(); HttpEntity<Object> httpEntity = new
     * HttpEntity<Object>( new AccountRegisterRequest(REGISTER_EMAIL_ALREADY_EXIST,
     * REGISTER_USERNAME, REGISTER_PASSWORD), headers);
     * 
     * restTemplate.exchange("/api/auth/register", HttpMethod.POST, httpEntity, new
     * ParameterizedTypeReference<>() { }); ; }
     */

    /*
     * @Test(expected = AccountAlreadyExistsException.class)
     * 
     * @Transactional
     * 
     * @Rollback(true) public void testRegisterUsernamelAlreadyExist() {
     * 
     * HttpHeaders headers = new HttpHeaders(); HttpEntity<Object> httpEntity = new
     * HttpEntity<Object>( new AccountRegisterRequest(REGISTER_EMAIL,
     * REGISTER_USERNAME_ALREADY_EXIST, REGISTER_PASSWORD), headers);
     * 
     * restTemplate.exchange("/api/auth/register", HttpMethod.POST, httpEntity, new
     * ParameterizedTypeReference<>() { }); ; }
     */

    @Test
    @Transactional
    @Rollback(true)
    public void testActivateAccount() {

        ResponseEntity<?> response = restTemplate.exchange("/api/auth/activate?token=" + DB_TOKEN_VALID, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(verificationTokenRepository.count(), DB_TOKENS_COUNT - 2);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testActivateAccountTokenExpired() {

        ResponseEntity<?> response = restTemplate.exchange("/api/auth/activate?token=" + DB_TOKEN_INVALID,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testResendActivationEmail() {

        ResponseEntity<?> response = restTemplate.exchange("/api/auth/resend?email=" + DB_ACTIVATION_EMAIL_VALID,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(verificationTokenRepository.count(), DB_TOKENS_COUNT + 1);
    }

    /*
     * @Test(expected = AccountNotFoundException.class)
     * 
     * @Transactional
     * 
     * @Rollback(true) public void testResendActivationEmailAccountDoesntExist() {
     * 
     * restTemplate.exchange("/api/auth/resend?email=" +
     * DB_ACTIVATION_EMAIL_INVALID, HttpMethod.GET, null, new
     * ParameterizedTypeReference<>() { }); }
     */

    /*
     * @Test(expected = AccountAlreadyActiveException.class)
     * 
     * @Transactional
     * 
     * @Rollback(true) public void testResendActivationEmailAlreadyActive() {
     * 
     * restTemplate.exchange("/api/auth/resend?email=" +
     * DB_ACTIVATION_EMAIL_ALREADY_ACTIVE, HttpMethod.GET, null, new
     * ParameterizedTypeReference<>() { }); }
     */

}
