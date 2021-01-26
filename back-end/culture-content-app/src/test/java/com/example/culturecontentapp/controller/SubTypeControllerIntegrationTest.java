package com.example.culturecontentapp.controller;
import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.SubTypeRequest;
import com.example.culturecontentapp.payload.response.AccountLoginResponse;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.repository.SubTypeRepository;
import com.example.culturecontentapp.util.RestPageImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.example.culturecontentapp.constants.SubTypeConstants.*;
import static com.example.culturecontentapp.constants.TypeConstants.*;
import static com.example.culturecontentapp.constants.UserConstants.DB_ADMIN_EMAIL;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubTypeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private SubTypeRepository repository;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<AccountLoginResponse> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username,password), AccountLoginResponse.class);
        accessToken = "Bearer " + responseEntity.getBody().getToken();
    }

    @Test
    public void testGetAllCulturalOfferSubTypes(){

        ResponseEntity<List<SubTypeResponse>> responseEntity =
                restTemplate.exchange(String.format("/api/sub-types/all?typeId=%d", DB_TYPE_ID), HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>(){});

        List<SubTypeResponse> subTypes = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SB_FIND_ALL_NUMBER_OF_ITEMS, subTypes.size());
        assertEquals(DB_SUBTYPE_NAME, subTypes.get(0).getName());
    }

    @Test
    public void testGetAllCulturalOfferSubTypesWithPagination(){

        ResponseEntity<RestPageImpl<SubTypeResponse>> responseEntity =
                restTemplate.exchange("/api/sub-types?typeId=1&page=0&size=2", HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>(){});

        RestPageImpl<SubTypeResponse> subTypes = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SB_FIND_ALL_NUMBER_OF_ITEMS, subTypes.getContent().size());
        assertEquals(DB_SUBTYPE_NAME, subTypes.getContent().get(0).getName());
    }

    @Test
    public void getById_validRequest_willReturnSucceed(){
        ResponseEntity<SubTypeResponse> responseEntity =
                restTemplate.exchange("/api/sub-types/1?typeId=1", HttpMethod.GET,
                        null,new ParameterizedTypeReference<>() {});

        SubTypeResponse response = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response);
        assertEquals(DB_SUBTYPE_ID,response.getId());
    }

    @Test
    public void getById_idDoesntExist_willReturnNotFound(){
        long TRASH_ID = 111L;
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(String.format("/api/sub-types/%d?typeId=1",TRASH_ID), HttpMethod.GET,
                        null,new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void create_validRequest_willReturnSucceed(){
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

    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void create_subTypeNameAlreadyExists_willReturnUnprocessable(){
        long BEFORE_CREATE_SIZE = repository.countAllByTypeId(DB_TYPE_ID);

        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubTypeRequest(DB_SUBTYPE_NAME),headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/sub-types?typeId=1", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(BEFORE_CREATE_SIZE, repository.count());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_validRequest_willReturnSucceed(){
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

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_SubTypeNameAlreadyExists_willReturnUnprocessable(){
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>
                (new SubTypeRequest(DB_SUBTYPE_ID,DB_SUBTYPE_WITHOUT_CULTURAL_OFFER_NAME),headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/sub-types/1?typeId=1", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_idWithoutCulturalOffer_willReturnSucceed(){
        long SIZE_BEFORE_DELETING = repository.countAllByTypeId(DB_TYPE_ID);
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null,headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(String.format("/api/sub-types/%d?typeId=1",SUBTYPE_ID_WITHOUT_CULTURAL_OFFER),
                        HttpMethod.DELETE, //treba id da bude 2
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SIZE_BEFORE_DELETING - 1, (long) repository.countAllByTypeId(DB_TYPE_ID));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_subTypeIdWithCulturalOffer_willReturnUnprocessable(){
        long SIZE_BEFORE_DELETING = repository.countAllByTypeId(DB_TYPE_ID);
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null,headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(String.format("/api/sub-types/%d?typeId=1",DB_SUBTYPE_ID),
                        HttpMethod.DELETE, //treba id da bude 2
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(SIZE_BEFORE_DELETING , (long) repository.countAllByTypeId(DB_TYPE_ID));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_subTypeIdDoesntExist_willReturnNotFound(){
        long SIZE_BEFORE_DELETING = repository.countAllByTypeId(DB_TYPE_ID);
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null,headers);

        long TRASH_ID = 1234L;
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(String.format("/api/sub-types/%d?typeId=1",TRASH_ID),
                        HttpMethod.DELETE,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(SIZE_BEFORE_DELETING , (long) repository.countAllByTypeId(DB_TYPE_ID));
    }
}
