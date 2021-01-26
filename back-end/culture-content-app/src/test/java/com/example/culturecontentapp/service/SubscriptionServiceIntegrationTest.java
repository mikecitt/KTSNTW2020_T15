package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.constants.SubscriptionConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.example.culturecontentapp.exception.UserAlreadySubscribedException;
import com.example.culturecontentapp.exception.UserNotSubscribedException;
import com.example.culturecontentapp.model.User;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.response.AccountLoginResponse;
import com.example.culturecontentapp.payload.response.SubscriptionResponse;
import com.example.culturecontentapp.repository.AccountRepository;
import com.example.culturecontentapp.security.jwt.TokenBasedAuthentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Jwts;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubscriptionServiceIntegrationTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    public void login(String user, String password) {
        ResponseEntity<AccountLoginResponse> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(user, password), AccountLoginResponse.class);

        String accessToken = "Bearer " + responseEntity.getBody().getToken();

        accessToken = accessToken.substring(7);

        String username = Jwts.parser().setSigningKey(SECREY_KEY).parseClaimsJws(accessToken).getBody().getSubject();

        if (username != null) {
            UserDetails account = userDetailsService.loadUserByUsername(username);

            TokenBasedAuthentication authentication = new TokenBasedAuthentication(accessToken, account);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    @Test
    @Transactional
    public void testGetSubscriptions() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        List<SubscriptionResponse> test = subscriptionService.get(pageable).getBody().getContent();
        int size = subscriptionService.get(pageable).getBody().getContent().size();
        assertEquals(DB_SUBSCRIPTION_SIZE, size);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSubscribe() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        subscriptionService.add(NOT_SUBSCRIBED_OFFER_ID);

        User u = (User) accountRepository.findByEmail(DB_USER_EMAIL).get();
        Integer SUBSCRIBE_COUNT = u.getSubscriptions().size();
        assertEquals(DB_SUBSCRIPTION_COUNT, SUBSCRIBE_COUNT - 1);
    }

    @Test(expected = UserAlreadySubscribedException.class)
    @Transactional
    @Rollback(true)
    public void testSubscribeAlreadySubscribed() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        subscriptionService.add(ALREADY_SUBSCRIBED_OFFER_ID);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUnSubscribe() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        subscriptionService.delete(ALREADY_SUBSCRIBED_OFFER_ID);

        User u = (User) accountRepository.findByEmail(DB_USER_EMAIL).get();
        Integer SUBSCRIBE_COUNT = u.getSubscriptions().size();
        assertEquals(DB_SUBSCRIPTION_COUNT, SUBSCRIBE_COUNT + 1);
    }

    @Test(expected = UserNotSubscribedException.class)
    @Transactional
    @Rollback(true)
    public void testUnSubscribeNotSubscribed() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        subscriptionService.delete(NOT_SUBSCRIBED_OFFER_ID);
    }
}
