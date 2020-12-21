package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.constants.NewsConstants.OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_TIME;
import static org.junit.Assert.assertEquals;

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
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setText(NEWS);
        NewsResponse created = newsService.create(newsRequest, OFFER_ID);
        assertEquals(newsRequest.getText(), created.getText());
    }
}
