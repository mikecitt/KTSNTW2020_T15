package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.constants.ReviewConstants.BAD_OFFER_ID;
import static com.example.culturecontentapp.constants.ReviewConstants.COMMENT;
import static com.example.culturecontentapp.constants.ReviewConstants.DB_REVIEW_SIZE;
import static com.example.culturecontentapp.constants.ReviewConstants.OFFER_ID;
import static com.example.culturecontentapp.constants.ReviewConstants.PAGEABLE_PAGE;
import static com.example.culturecontentapp.constants.ReviewConstants.PAGEABLE_SIZE;
import static com.example.culturecontentapp.constants.ReviewConstants.RATING;
import static org.junit.Assert.assertEquals;

import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.ReviewRequest;
import com.example.culturecontentapp.payload.response.ReviewResponse;
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

import static com.example.culturecontentapp.constants.ReviewConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TestRestTemplate restTemplate;

    public void login(String user, String password) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(user, password), String.class);

        String accessToken = "Bearer " + responseEntity.getBody();

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
    public void testGetOffersReviews() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        int size = reviewService.get(OFFER_ID, pageable).getBody().getContent().size();
        assertEquals(DB_REVIEW_SIZE, size);
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void testGetOffersReviewsOfferDoesntExist() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        reviewService.get(BAD_OFFER_ID, pageable).getBody().getContent().size();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testaddReview() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        ReviewRequest addReviewRequest = new ReviewRequest(RATING, COMMENT);
        ReviewResponse added = reviewService.addReview(OFFER_ID, addReviewRequest).getBody();
        assertEquals(addReviewRequest.getComment(), added.getComment());
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    @Transactional
    @Rollback(true)
    public void testaddReviewOfferDoesntExist() {
        login(DB_USER_EMAIL, DB_USER_PASSWORD);

        ReviewRequest addReviewRequest = new ReviewRequest(RATING, COMMENT);
        reviewService.addReview(BAD_OFFER_ID, addReviewRequest);
    }

}
