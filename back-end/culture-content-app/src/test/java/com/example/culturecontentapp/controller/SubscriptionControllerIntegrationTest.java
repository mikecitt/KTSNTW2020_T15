package com.example.culturecontentapp.controller;

import static com.example.culturecontentapp.constants.SubscriptionConstants.*;
import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import com.example.culturecontentapp.model.User;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.response.AccountLoginResponse;
import com.example.culturecontentapp.payload.response.SubscriptionResponse;
import com.example.culturecontentapp.repository.AccountRepository;
import com.example.culturecontentapp.util.RestPageImpl;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubscriptionControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<AccountLoginResponse> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username, password), AccountLoginResponse.class);
        accessToken = "Bearer " + responseEntity.getBody().getToken();
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSubscribe() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/subscriptions?id=" + NOT_SUBSCRIBED_OFFER_ID,
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        User u = (User) accountRepository.findByEmail(DB_USER_EMAIL).get();
        Integer SUBSCRIBE_COUNT = u.getSubscriptions().size();
        assertEquals(DB_SUBSCRIPTION_COUNT, SUBSCRIBE_COUNT - 1);
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testSubscribeAlreadySubscribed() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/subscriptions?id=" + ALREADY_SUBSCRIBED_OFFER_ID, HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUnSubscribe() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/subscriptions?id=" + ALREADY_SUBSCRIBED_OFFER_ID, HttpMethod.DELETE, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        User u = (User) accountRepository.findByEmail(DB_USER_EMAIL).get();
        Integer SUBSCRIBE_COUNT = u.getSubscriptions().size();
        assertEquals(DB_SUBSCRIPTION_COUNT, SUBSCRIBE_COUNT + 1);
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUnSubscribeNotSubscribed() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/subscriptions?id=" + NOT_SUBSCRIBED_OFFER_ID,
                HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    public void testGetSubscriptions() {

        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null, headers);

        ResponseEntity<RestPageImpl<SubscriptionResponse>> responseEntity = restTemplate.exchange(
                "/api/subscriptions?page=0&size=5", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
                });

        RestPageImpl<SubscriptionResponse> subscriptions = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_SUBSCRIPTION_SIZE, subscriptions.getContent().size());
    }
}
