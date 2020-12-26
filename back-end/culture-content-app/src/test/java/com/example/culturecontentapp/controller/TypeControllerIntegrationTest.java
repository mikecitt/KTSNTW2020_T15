package com.example.culturecontentapp.controller;

import com.example.culturecontentapp.model.Type;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.TypeRequest;
import com.example.culturecontentapp.payload.response.TypeResponse;
import com.example.culturecontentapp.repository.TypeRepository;
import com.example.culturecontentapp.security.jwt.TokenBasedAuthentication;
import com.example.culturecontentapp.service.TypeService;
import com.example.culturecontentapp.util.RestPageImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.culturecontentapp.constants.UserConstants.*;
import static com.example.culturecontentapp.constants.TypeConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class TypeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TypeRepository typeRepository;

    private String accessToken;


    public void login(String username, String password) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username,password), String.class);
        accessToken = "Bearer " + responseEntity.getBody();
    }

    @Test
    public void testGetAllCulturalOfferTypes(){

         //a trebalo bi Page<TypeResponse>
        ResponseEntity<RestPageImpl<TypeResponse>> responseEntity =
                restTemplate.exchange("/api/types?page=0&size=2", HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>(){});

        RestPageImpl<TypeResponse> types = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, types.getContent().size());
        assertEquals(DB_TYPE, types.getContent().get(0).getName());
    }

    @Test
    public void getById_validRequest_willReturnSucceed(){

        ResponseEntity<TypeResponse> responseEntity =
                restTemplate.getForEntity("/api/types/1", TypeResponse.class);
        TypeResponse typeResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(typeResponse);
        assertEquals(DB_TYPE_ID,typeResponse.getId());
    }

    @Test
    public void getById_idDoesntExist_willReturnNotFound(){
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/types/10",HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void create_validRequest_willReturnSucceed(){
        long BEFORE_CREATE_SIZE = typeRepository.count();
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new TypeRequest(NEW_TYPE),headers);

        ResponseEntity<Void> responseEntity =
            restTemplate.exchange("/api/types", HttpMethod.POST,
                    httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(BEFORE_CREATE_SIZE + 1, typeRepository.count());

        Type type = typeRepository.findAll().get((int) BEFORE_CREATE_SIZE);
        assertEquals(NEW_TYPE, type.getName());

//        typeService.delete(type.getId());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void create_subTypeNameAlreadyExists_willReturnUnprocessable(){
        long BEFORE_CREATE_SIZE = typeRepository.count();
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new TypeRequest(DB_TYPE),headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/types", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(BEFORE_CREATE_SIZE, typeRepository.count());
        assertNull(responseEntity.getBody());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_validRequest_willReturnSucceed(){
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new TypeRequest(DB_TYPE_ID,NEW_TYPE),headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/types/1", HttpMethod.PUT,
                            httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Optional<Type> typeOptional = typeRepository.findById(DB_TYPE_ID);
        assertTrue(typeOptional.isPresent());

        Type type = typeOptional.get();
        assertEquals(DB_TYPE_ID, type.getId());
        assertEquals(NEW_TYPE, type.getName());

//        type.update(DB_TYPE);
//        typeRepository.save(type);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_SubTypeNameAlreadyExists_willReturnUnprocessable(){
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new TypeRequest(DB_TYPE_ID,DB_TYPE_WITHOUT_SUBTYPE),headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/types/1", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void update_wrongSubTypeId_willReturnUnprocessable(){
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new TypeRequest(DB_TYPE_ID,NEW_TYPE),headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/types/11", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_withoutSubType_willReturnSucceed(){
        long sizeAffterPutting = typeRepository.count();

        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null,headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(String.format("/api/types/%d",DB_TYPE_WITHOUT_SUBTYPE_ID), HttpMethod.DELETE,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(sizeAffterPutting - 1, typeRepository.count());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_withSubType_willReturnUnprocessable(){
        long sizeAffterPutting = typeRepository.count();

        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null,headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(String.format("/api/types/%d",DB_TYPE_ID), HttpMethod.DELETE,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(sizeAffterPutting, typeRepository.count());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void delete_subTypeIdDoesntExist_willReturnUnprocessable(){
        long sizeAffterPutting = typeRepository.count();

        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null,headers);

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(String.format("/api/types/%d",TYPE_DOESNT_EXISTS), HttpMethod.DELETE,
                        httpEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(sizeAffterPutting, typeRepository.count());
    }
}
