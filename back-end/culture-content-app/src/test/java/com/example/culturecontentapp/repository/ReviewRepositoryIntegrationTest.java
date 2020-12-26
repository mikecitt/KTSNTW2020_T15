package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.Review;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.culturecontentapp.constants.ReviewConstants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ReviewRepositoryIntegrationTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testFindByCulturalOffer() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Review> found = reviewRepository.findByCulturalOffer(OFFER_ID, pageable);
        assertEquals(DB_REVIEW_SIZE, found.getNumberOfElements());
    }
}
