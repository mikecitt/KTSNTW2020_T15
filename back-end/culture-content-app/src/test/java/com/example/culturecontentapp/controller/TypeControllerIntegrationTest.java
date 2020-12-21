package com.example.culturecontentapp.controller;

import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.TypeRequest;
import com.example.culturecontentapp.payload.response.TypeResponse;
import com.example.culturecontentapp.security.jwt.TokenBasedAuthentication;
import com.example.culturecontentapp.service.TypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // JWT token za pristup REST servisima. Bice dobijen pri logovanju
     private String accessToken;


    public void login(String username, String password) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username,password), String.class);
        accessToken = "Bearer " + responseEntity.getBody();
    }

//    @Test
//    public void testGetAllCulturalOfferTypes(){
//
//         //a trebalo bi Page<TypeResponse>
//        ResponseEntity<TypeResponse[]> responseEntity =
//                restTemplate.getForEntity("/api/types?page=0&size=1",
//                        TypeResponse[].class);
//
//        TypeResponse[] types = responseEntity.getBody();
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, types.length);
//        assertEquals(DB_TYPE, types[0].getName());
//    }

    @Test
    public void testGetCulturalOfferType(){

        ResponseEntity<TypeResponse> responseEntity =
                restTemplate.getForEntity("/api/types/1001", TypeResponse.class);
        TypeResponse typeResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(typeResponse);
        assertEquals(DB_TYPE_ID,typeResponse.getId());
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testCreateCulturalOfferType(){
        int size = typeService.findAll().size();
        login(DB_ADMIN_EMAIL, "qwerty");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
//         kreiramo objekat koji saljemo u sklopu zahteva
//         objekat nema telo, vec samo zaglavlje, jer je rec o GET zahtevu
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new TypeRequest(NEW_TYPE),headers);

        ResponseEntity<TypeResponse> responseEntity =
            restTemplate.postForEntity("/api/types",
                    httpEntity, TypeResponse.class);

        TypeResponse typeResponse = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(typeResponse);
        assertEquals(NEW_TYPE, typeResponse.getName());

        List<TypeResponse> types = typeService.findAll();
        assertEquals(size+1, types.size());
        assertEquals(NEW_TYPE, types.get(types.size() - 1).getName());

        typeService.delete(typeResponse.getId());
    }


}
