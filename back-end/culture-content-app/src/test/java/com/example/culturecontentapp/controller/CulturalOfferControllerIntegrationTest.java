package com.example.culturecontentapp.controller;

import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_DESCRIPTION;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.DB_ADMIN_EMAIL;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.DB_ADMIN_PASSWORD;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_PAGE_ZERO;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_PAGE_ONE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_SIZE_ONE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_SIZE_TWO;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_ID_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_ID_NOT_EXISTS;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.transaction.Transactional;

import static java.util.Arrays.asList;

import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.NewCulturalOfferRequest;
import com.example.culturecontentapp.payload.response.NewCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.SelectCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.service.CulturalOfferService;
import com.example.culturecontentapp.util.RestPageImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferControllerIntegrationTest {

    @Autowired
    private CulturalOfferService service;
    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login",
                new AccountLoginRequest(username, password), String.class);
        accessToken = "Bearer " + responseEntity.getBody();
    }

    @Test
    public void searchAndFilter_validRequest_willReturnSucceed() {
        ResponseEntity<List<SubTypeResponse>> responseEntity = restTemplate.exchange(
                "/api/cultural-offer/search?culturalOfferName=ex&subTypeName=&typeName=", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
    }

    @Test
    public void searchAndFilter_noResultsRequest_willReturnSucceed() {
        ResponseEntity<List<SubTypeResponse>> responseEntity = restTemplate.exchange(
                "/api/cultural-offer/search?culturalOfferName=ex&subTypeName=Manifestacija&typeName=", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    @Transactional
    public void insert_InsertedSucessfully() throws IOException {
        login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", accessToken);

        NewCulturalOfferRequest request = new NewCulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);

        byte[] fileContent = "this is file content".getBytes();
        Path tempFile = Files.createTempFile("upload-file", ".txt");

        Files.write(tempFile, fileContent);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("files", new FileSystemResource(tempFile.toFile()));
        body.add("request", request);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<NewCulturalOfferResponse> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/cultural-offer?subTypeId={subTypeId}", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {
                }, CULTURAL_OFFER_SUBTYPE);

        // NewCulturalOfferResponse newsResponse = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // assertNotNull(newsResponse);
    }

    @Test
    public void insert_SelectReturnsTwo_WhenPageSizeIsTwo() throws IOException {
        ResponseEntity<RestPageImpl<SelectCulturalOfferResponse>> response = restTemplate.exchange(
                "/api/cultural-offer?size={pageSize}", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                }, PAGEABLE_SIZE_TWO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(0, response.getBody().getNumber());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void findAll_ReturnsOne_WhenPageSizeIsOne() {
        ResponseEntity<RestPageImpl<SelectCulturalOfferResponse>> response = restTemplate.exchange(
                "/api/cultural-offer?page={page}&size={pageSize}", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }, PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_ONE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getTotalPages());
        assertEquals(0, response.getBody().getNumber());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findAll_ReturnsPageTwo_WhenPageIsTwo() {
        ResponseEntity<RestPageImpl<SelectCulturalOfferResponse>> response = restTemplate.exchange(
                "/api/cultural-offer?page={page}&size={pageSize}", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }, PAGEABLE_PAGE_ONE, PAGEABLE_SIZE_ONE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getTotalPages());
        assertEquals(1, response.getBody().getNumber());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findByBy_ReturnsResult_WhenResultExists() {
        ResponseEntity<SelectCulturalOfferResponse> response = restTemplate.exchange("/api/cultural-offer/{id}",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                }, CULTURAL_OFFER_ID_EXISTS);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CULTURAL_OFFER_ID_EXISTS, response.getBody().getId());
    }

    @Test
    public void findByBy_ReturnsNothing_WhenResultNotExists() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(MediaType.TEXT_PLAIN));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/cultural-offer/{id}", HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<>() {
                }, CULTURAL_OFFER_ID_NOT_EXISTS);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
