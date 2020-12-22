package com.example.culturecontentapp.controller;

import static com.example.culturecontentapp.constants.NewsConstants.NEWS;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_TIME;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_ID;
import static com.example.culturecontentapp.constants.NewsConstants.DB_NEWS;
import static com.example.culturecontentapp.constants.NewsConstants.OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.BAD_OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.BAD_NEWS_ID;
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
import com.example.culturecontentapp.util.RestPageImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        
        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{offer_id}",
                                                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){}, OFFER_ID);

        NewsResponse newsResponse = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(newsResponse);
        assertEquals(NEWS, newsResponse.getText());

        List<NewsResponse> news = newsService.getOffersNews(OFFER_ID, pageable).getContent();
        assertEquals(size + 1, news.size());
        assertEquals(NEWS, news.get(news.size() - 1).getText());

        newsService.deleteNews(newsResponse.getId());
        
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateNewsCulturalOfferDoesntExist(){        
        
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{offer_id}",
                                                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){}, BAD_OFFER_ID);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Cultural offer doesn't exist", responseEntity.getBody());
        
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateNews(){

        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest(NEWS_ID, NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{id}",
                                            HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>(){}, 1);

        NewsResponse newsResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(newsResponse);
        assertEquals(NEWS, newsResponse.getText());
        assertEquals(NEWS_ID, newsResponse.getId());

        assertEquals(NEWS_ID, newsResponse.getId());
        assertEquals(NEWS, newsResponse.getText());

        newsRequest.setText(DB_NEWS);
        newsService.update(newsRequest, NEWS_ID);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateNewsDoesntExist(){

        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest(NEWS_ID, NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{id}",
                                        HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>(){}, BAD_NEWS_ID);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("The news you want to update doesn't exist.", responseEntity.getBody());

    }

    @Test
    @Transactional
    public void testGetOffersNews(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        ParameterizedTypeReference<RestPageImpl<NewsResponse>> responseType = new ParameterizedTypeReference<RestPageImpl<NewsResponse>>() { };

        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<RestPageImpl<NewsResponse>> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/culturalOffer/{offer_id}",
                                        HttpMethod.GET, httpEntity, responseType, OFFER_ID);
        
        List<NewsResponse> news = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_NEWS, news.get(0).getText());
        
    }

    @Test
    @Transactional
    public void testGetOffersNewsOfferDoesntExist(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/culturalOffer/{offer_id}",
                                        HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>(){}, BAD_OFFER_ID);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Cultural offer doesn't exist", responseEntity.getBody());
        
    }

    @Test
    @Transactional
    public void testFindNews(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{id}",
                                        HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>(){}, NEWS_ID);
        assertEquals(NEWS_ID, responseEntity.getBody().getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    @Transactional
    public void testFindNewsDoesntExist(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{id}",
                                        HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>(){}, BAD_NEWS_ID);
        assertEquals("The news doesn't exist.", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteNews(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsResponse created = newsService.create(new NewsRequest(NEWS, NEWS_TIME), OFFER_ID);

        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        int size = newsService.getOffersNews(OFFER_ID, pageable).getContent().size();

        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{id}",
                                        HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>(){}, NEWS_ID + 2);

        int sizeAfter = newsService.getOffersNews(OFFER_ID, pageable).getContent().size();
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, sizeAfter);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteNewsDoesntExist(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/api/news/{id}",
                                        HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>(){}, BAD_NEWS_ID);

        
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("The news you want to delete doesn't exist.", responseEntity.getBody());

    }
}
