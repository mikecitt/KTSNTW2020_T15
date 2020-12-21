package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.constants.NewsConstants.CATEGORY_ID;
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



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceIntegrationTest {
    
    @Autowired
    private NewsService newsService;

    @Test   
    public void testCreate(){
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setText(NEWS);
        newsRequest.setDate(NEWS_TIME);
        NewsResponse created = newsService.create(newsRequest, CATEGORY_ID);
        assertEquals(newsRequest.getText(), created.getText());
    }
}
