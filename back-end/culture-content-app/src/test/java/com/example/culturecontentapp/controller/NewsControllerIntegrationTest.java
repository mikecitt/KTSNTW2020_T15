package com.example.culturecontentapp.controller;

import static com.example.culturecontentapp.constants.NewsConstants.NEWS;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_TIME;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_ID;
import static com.example.culturecontentapp.constants.NewsConstants.OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_PAGE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_SIZE;
import static com.example.culturecontentapp.constants.NewsConstants.DB_ADMIN_EMAIL;
import static com.example.culturecontentapp.constants.NewsConstants.DB_ADMIN_PASSWORD;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.payload.response.NewsResponse;
import com.example.culturecontentapp.service.NewsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NewsService newsService;
    
    // JWT token za pristup REST servisima. Bice dobijen pri logovanju
    private String accessToken;
    
    public void login(String username, String password) {
        AccountLoginRequest loginRequest = new AccountLoginRequest(username, password);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/api/auth/login",
                loginRequest, String.class);
        accessToken = "Bearer " + responseEntity.getBody();
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testCreateNews(){        
        
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        int size = newsService.getOffersNews(OFFER_ID, pageable).getContent().size();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setText(NEWS);
        newsRequest.setDate(NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        


        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{offer_id}", HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){}, 1);


        NewsResponse newsResponse = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(newsResponse);
        assertEquals(NEWS, newsResponse.getText());

        List<NewsResponse> news = newsService.getOffersNews(OFFER_ID, pageable).getContent();
        assertEquals(size + 1, news.size());
        assertEquals(NEWS, news.get(news.size() - 1).getText());

        newsService.deleteNews(NEWS_ID);
        
    }
}
