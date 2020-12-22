package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.constants.NewsConstants.OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.BAD_OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.BAD_NEWS_ID;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_ID;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_TIME;
import static org.junit.Assert.assertEquals;

import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.NewsNotFoundException;
import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.payload.response.NewsResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceIntegrationTest {
    
    @Autowired
    private NewsService newsService;

    @Test   
    @Transactional
    public void testCreate(){
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);
        NewsResponse created = newsService.create(newsRequest, OFFER_ID);
        assertEquals(newsRequest.getText(), created.getText());
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    @Transactional
    public void testCreateOfferDoesntExist(){
        newsService.create(new NewsRequest(NEWS, NEWS_TIME), BAD_OFFER_ID);
    }

    @Test
    @Transactional
    public void testUpdate(){
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);
        NewsResponse created = newsService.update(newsRequest, NEWS_ID);
        assertEquals(NEWS, created.getText());
    }

    @Test(expected = NewsNotFoundException.class)
    @Transactional
    public void testUpdateNewsDoesntExist(){
        newsService.update(new NewsRequest(NEWS, NEWS_TIME), BAD_NEWS_ID);
    }
}
