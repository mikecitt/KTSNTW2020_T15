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
import com.example.culturecontentapp.payload.response.AccountLoginResponse;
import com.example.culturecontentapp.payload.response.NewsResponse;
import com.example.culturecontentapp.service.NewsService;
import com.example.culturecontentapp.util.RestPageImpl;

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
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<AccountLoginResponse> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username,password), AccountLoginResponse.class);
        accessToken = "Bearer " + responseEntity.getBody().getToken();
    }

    @Test
    @Transactional
    public void getByOfferId_GetsSuccessfully(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        ParameterizedTypeReference<RestPageImpl<NewsResponse>> responseType = new ParameterizedTypeReference<RestPageImpl<NewsResponse>>() { };

        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<RestPageImpl<NewsResponse>> responseEntity = restTemplate.exchange("/api/news/culturalOffer/{offer_id}",
                                        HttpMethod.GET, httpEntity, responseType, OFFER_ID);
        
        List<NewsResponse> news = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_NEWS, news.get(0).getText()); //rollback ne radi kako treba
    
        
    }

    @Test
    @Transactional
    public void create_validParams_willReturnSucceed(){        
        
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        
        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange("/api/news/{offer_id}",
                                                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){}, OFFER_ID);

        NewsResponse newsResponse = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(newsResponse);
        assertEquals(NEWS, newsResponse.getText());

        List<NewsResponse> news = newsService.getOffersNews(OFFER_ID, pageable).getBody().getContent();
        assertEquals(NEWS, news.get(news.size() - 1).getText());
        newsService.deleteNews(newsResponse.getId());
        
    }

    @Test
    @Transactional
    public void create_ThrowsCulturalOfferNotFoundException_WhenOfferIdNotExists(){        
        
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/news/{offer_id}",
                                                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){}, BAD_OFFER_ID);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Cultural offer doesn't exist", responseEntity.getBody());
        
    }

    @Test
    @Transactional
    public void update_UpdatesSuccessfully(){

        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest(NEWS_ID, NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange("/api/news/{id}",
                                            HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>(){}, 1);

        NewsResponse newsResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(newsResponse);
        assertEquals(NEWS, newsResponse.getText());
        assertEquals(NEWS_ID, newsResponse.getId());

        assertEquals(NEWS_ID, newsResponse.getId());
        assertEquals(NEWS, newsResponse.getText());

        newsRequest.setText(DB_NEWS);
        restTemplate.exchange("/api/news/{id}",
                                            HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>(){}, 1);

    }

    @Test
    @Transactional
    public void update_ThrowsNotFoundException_WhenIdNotExists(){

        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest(NEWS_ID, NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/news/{id}",
                                        HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>(){}, BAD_NEWS_ID);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("The news you want to update doesn't exist.", responseEntity.getBody());

    }

    

    @Test
    @Transactional
    public void getByOfferId_ThrowsOfferNotFound_WhenIdNotExist(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/news/culturalOffer/{offer_id}",
                                        HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>(){}, BAD_OFFER_ID);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Cultural offer doesn't exist", responseEntity.getBody());
        
    }

    @Test
    @Transactional
    public void findByCulturalOffer_validParams_shouldSucceed(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange("/api/news/{id}",
                                        HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>(){}, NEWS_ID);
        assertEquals(NEWS_ID, responseEntity.getBody().getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    @Transactional
    public void findById_ThrowsNotFoundException_WhenIdNotExist(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/news/{id}",
                                        HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>(){}, BAD_NEWS_ID);
        assertEquals("The news doesn't exist.", responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    @Transactional
    public void delete_shouldReturnSuccess(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(newsRequest, headers);
        
        ResponseEntity<NewsResponse> createdEntity = restTemplate.exchange("/api/news/{offer_id}",
                                                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){}, OFFER_ID);
        // Long createdId = newsService.create(new NewsRequest(NEWS, NEWS_TIME), OFFER_ID).getBody().getId();

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/news/{id}",
                                        HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>(){}, createdEntity.getBody().getId());

        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    @Transactional
    public void delete_ThrowsNotFoundException_WhenIdNotExists(){
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/news/{id}",
                                        HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>(){}, BAD_NEWS_ID);

        
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("The news you want to delete doesn't exist.", responseEntity.getBody());

    }
}
