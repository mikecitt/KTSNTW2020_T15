package com.example.culturecontentapp.controller;

import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.SubTypeRequest;
import com.example.culturecontentapp.payload.request.TypeRequest;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.payload.response.TypeResponse;
import com.example.culturecontentapp.repository.SubTypeRepository;
import com.example.culturecontentapp.service.SubTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;

import java.util.Optional;

import static com.example.culturecontentapp.constants.SubTypeConstants.*;
import static com.example.culturecontentapp.constants.TypeConstants.DB_TYPE_ID;
import static com.example.culturecontentapp.constants.TypeConstants.NEW_TYPE;
import static com.example.culturecontentapp.constants.UserConstants.DB_ADMIN_EMAIL;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubTypeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private SubTypeService service;
    @Autowired
    private SubTypeRepository repository;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username,password), String.class);
        accessToken = "Bearer " + responseEntity.getBody();
    }

    @Test
    public void whenValidIdThenSubTypeShouldBeFound(){
        ResponseEntity<SubTypeResponse> responseEntity =
                restTemplate.exchange("/api/sub-types?typeId=1", HttpMethod.GET,
                        null,new ParameterizedTypeReference<>() {});

        SubTypeResponse response = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response);
        assertEquals(DB_SUBTYPE_ID,response.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void whenValidRequestThenSubTypeShouldBeCreatedsuccessfully(){
        long BEFORE_CREATE_SIZE = repository.count();

        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubTypeRequest(NEW_SUBTYPE_NAME),headers);

        ResponseEntity<SubTypeResponse> responseEntity =
                restTemplate.exchange("/api/sub-types?typeId=1", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<>() {});

        SubTypeResponse subTypeResponse = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(BEFORE_CREATE_SIZE+1, repository.count());
        assertEquals(NEW_SUBTYPE_NAME,subTypeResponse.getName());

        SubType sub = repository.findAll().get((int) BEFORE_CREATE_SIZE);
        assertEquals(NEW_SUBTYPE_NAME, sub.getName());

        repository.delete(sub);
    }
    @Test
    public void whenValidRequestThenSubTypeShouldBeUpdatedSuccessfully(){
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubTypeRequest(DB_SUBTYPE_ID,NEW_SUBTYPE_NAME),headers);

        ResponseEntity<SubTypeResponse> responseEntity =
                restTemplate.exchange("/api/sub-types/1?typeId=1", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<>() {});

        SubTypeResponse response = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_SUBTYPE_NAME, response.getName());

        SubType updated = repository.findByIdAndTypeId(DB_SUBTYPE_ID, DB_TYPE_ID);
        assertNotNull(updated);
        assertEquals(NEW_SUBTYPE_NAME,updated.getName());

        updated.setName(DB_SUBTYPE_NAME);
        repository.save(updated);
    }
    @Test
    public void whenGivenSubTypeIsWithoutOfferThenSubTypeShouldBeDeletedSuccessfully(){
        long SIZE_BEFORE_DELETING = repository.countAllByTypeId(DB_TYPE_ID);
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubTypeRequest(DB_SUBTYPE_ID,NEW_SUBTYPE_NAME),headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/sub-types/1?typeId=1", HttpMethod.DELETE,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SIZE_BEFORE_DELETING - 1, (long) repository.countAllByTypeId(DB_TYPE_ID));

    }

}
