package com.example.culturecontentapp.controller;

import static com.example.culturecontentapp.constants.ReviewConstants.DB_REVIEW_SIZE;
import static com.example.culturecontentapp.constants.ReviewConstants.DB_USER_EMAIL;
import static com.example.culturecontentapp.constants.ReviewConstants.DB_USER_PASSWORD;
import static com.example.culturecontentapp.constants.ReviewConstants.OFFER_ID;
import static org.junit.Assert.assertEquals;

import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.AddReviewRequest;
import com.example.culturecontentapp.payload.response.AddReviewResponse;
import com.example.culturecontentapp.payload.response.ReviewResponse;
import com.example.culturecontentapp.repository.ReviewRepository;
import com.example.culturecontentapp.service.ReviewService;
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

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username, password), String.class);
        accessToken = "Bearer " + responseEntity.getBody();
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
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testAddReview() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new AddReviewRequest(Short.valueOf("3"), "beautiful"),
                headers);

        ResponseEntity<AddReviewResponse> responseEntity = restTemplate.exchange("/api/review?id=" + OFFER_ID,
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                });

        // assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // User u = (User) accountRepository.findByEmail(DB_USER_EMAIL).get();
        // Integer SUBSCRIBE_COUNT = u.getSubscriptions().size();
        // assertEquals(DB_SUBSCRIPTION_SIZE, SUBSCRIBE_COUNT - 1);
    }
}
