package com.example.culturecontentapp.controller;

import static com.example.culturecontentapp.constants.ReviewConstants.*;
import static org.junit.Assert.assertEquals;

import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.ReviewRequest;
import com.example.culturecontentapp.payload.response.AccountLoginResponse;
import com.example.culturecontentapp.payload.response.ReviewResponse;
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
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReviewControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<AccountLoginResponse> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username, password), AccountLoginResponse.class);
        accessToken = "Bearer " + responseEntity.getBody().getToken();
    }

    @Test
    public void testGetReviewsOfCulturalOffer() {

        ResponseEntity<RestPageImpl<ReviewResponse>> responseEntity = restTemplate.exchange(
                "/api/review?page=0&size=2&culturalOfferId=" + OFFER_ID, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        RestPageImpl<ReviewResponse> types = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_REVIEW_SIZE, types.getContent().size());
    }

    @Test
    public void testGetOffersReviewsOfferDoesntExist() {
        ResponseEntity<String> resp = restTemplate.exchange("/api/review?page=0&size=2&culturalOfferId=" + BAD_OFFER_ID,
                HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }

    @Test
    @Transactional
    public void testaddReview() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        ReviewRequest addReviewRequest = new ReviewRequest(RATING, COMMENT);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(addReviewRequest, headers);

        ResponseEntity<ReviewRequest> response = restTemplate.exchange("/api/review?culturalOfferId=" + OFFER_ID,
                HttpMethod.POST, httpEntity, ReviewRequest.class);

        assertEquals(addReviewRequest.getComment(), response.getBody().getComment());
    }

    @Test
    @Transactional
    public void testaddReviewOfferDoesntExist() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        ReviewRequest addReviewRequest = new ReviewRequest(RATING, COMMENT);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(addReviewRequest, headers);

        ResponseEntity<String> resp = restTemplate.exchange("/api/review?culturalOfferId=" + BAD_OFFER_ID,
                HttpMethod.POST, httpEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }
}
