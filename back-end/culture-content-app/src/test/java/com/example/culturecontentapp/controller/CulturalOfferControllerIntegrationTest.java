package com.example.culturecontentapp.controller;

import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.service.CulturalOfferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferControllerIntegrationTest {

    @Autowired
    private CulturalOfferService service;
    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username,password), String.class);
        accessToken = "Bearer " + responseEntity.getBody();
    }

    @Test
    public void searchAndFilter_validRequest_willReturnSucceed(){
        ResponseEntity<List<SubTypeResponse>> responseEntity =
                restTemplate.exchange("/api/cultural-offer/search?culturalOfferName=ex&subTypeName=&typeName=",
                        HttpMethod.GET,
                        null, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
    }

    @Test
    public void searchAndFilter_noResultsRequest_willReturnSucceed(){
        ResponseEntity<List<SubTypeResponse>> responseEntity =
                restTemplate.exchange("/api/cultural-offer/search?culturalOfferName=ex&subTypeName=Manifestacija&typeName=",
                        HttpMethod.GET,
                        null, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }
}
