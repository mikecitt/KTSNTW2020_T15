package com.example.culturecontentapp.controller;

import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_DESCRIPTION;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_EXISTS_2;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.DB_ADMIN_EMAIL;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.DB_ADMIN_PASSWORD;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_PAGE_ZERO;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_PAGE_ONE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_SIZE_ONE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_SIZE_TWO;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_ID_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_ID_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.STORAGE_FILE_NOT_VALID;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_NEW;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import com.example.culturecontentapp.payload.request.AccountLoginRequest;
import com.example.culturecontentapp.payload.request.CulturalOfferLocationRequest;
import com.example.culturecontentapp.payload.request.CulturalOfferRequest;
import com.example.culturecontentapp.payload.response.AccountLoginResponse;
import com.example.culturecontentapp.payload.response.CulturalOfferResponse;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.util.RestPageImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION_LONGITUDE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION_ADDRESS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION_LATITUDE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferControllerIntegrationTest {

        @Autowired
        private TestRestTemplate restTemplate;

        private String accessToken;

        public void login(String username, String password) {
                ResponseEntity<AccountLoginResponse> responseEntity = restTemplate.postForEntity("/api/auth/login",
                                new AccountLoginRequest(username, password), AccountLoginResponse.class);
                accessToken = "Bearer " + responseEntity.getBody().getToken();
        }

        @Test
        @Transactional
        public void searchAndFilter_validRequest_willReturnSucceed() {
                ResponseEntity<List<SubTypeResponse>> responseEntity = restTemplate.exchange(
                                "/api/cultural-offer/search?culturalOfferName=ex&subTypeName=&typeName=",
                                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                                });

                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                assertEquals(1, responseEntity.getBody().size());
        }

        @Test
        @Transactional
        public void searchAndFilter_noResultsRequest_willReturnSucceed() {
                ResponseEntity<List<SubTypeResponse>> responseEntity = restTemplate.exchange(
                                "/api/cultural-offer/search?culturalOfferName=ex&subTypeName=Manifestacija&typeName=",
                                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                                });

                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                assertEquals(0, responseEntity.getBody().size());
        }

        @Test
        @Transactional
        public void insert_ReturnsAlreadyExists() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_EXISTS_2,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);

                HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

                ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-offer?subTypeId={subTypeId}",
                                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_SUBTYPE);

                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        }

        @Test
        @Transactional
        public void insert_ReturnsSubTypeNotFound() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);

                HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

                ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-offer?subTypeId={subTypeId}",
                                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_SUBTYPE_NOT_EXISTS);

                assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }

        @Test
        @Transactional
        public void insert_ReturnsStorageBadRequest() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[] { STORAGE_FILE_NOT_VALID });

                HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

                ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-offer?subTypeId={subTypeId}",
                                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_SUBTYPE);

                assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        }

        @Test
        @Transactional
        public void insert_InsertedSucessfully() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NEW,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);

                HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

                ResponseEntity<CulturalOfferResponse> responseEntity = restTemplate.exchange(
                                "/api/cultural-offer?subTypeId={subTypeId}", HttpMethod.POST, httpEntity,
                                new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_SUBTYPE);

                assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
                assertEquals(request.getName(), responseEntity.getBody().getName());
                assertEquals(request.getDescription(), responseEntity.getBody().getDescription());
        }

        @Test
        public void update_ReturnsNotFound() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);

                HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

                ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-offer/{id}", HttpMethod.PUT,
                                httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_ID_NOT_EXISTS);

                assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }

        @Test
        public void update_ReturnsNameAlreadyExists() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);

                HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

                ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-offer/{id}", HttpMethod.PUT,
                                httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_ID_EXISTS);

                assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        }

        @Test
        public void update_ReturnsStorageNotValid() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[] { STORAGE_FILE_NOT_VALID });

                HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

                ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-offer/{id}", HttpMethod.PUT,
                                httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_ID_EXISTS);

                assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        }

        @Test
        @Transactional
        public void update_UpdatesSucessfully() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                CulturalOfferRequest request = new CulturalOfferRequest("Bla bla bla", CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);

                HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

                ResponseEntity<CulturalOfferResponse> responseEntity = restTemplate.exchange("/api/cultural-offer/{id}",
                                HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_ID_EXISTS);

                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                // assertEquals(request.getName(), responseEntity.getBody().getName());
                // assertEquals(request.getDescription(),
                // responseEntity.getBody().getDescription());
        }

        @Test
        public void delete_ReturnsNotFound() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);

                ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-offer/{id}",
                                HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_ID_NOT_EXISTS);

                assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }

        @Test
        @Transactional
        public void delete_DeletesSucessfully() throws IOException {
                login(DB_ADMIN_EMAIL, DB_ADMIN_PASSWORD);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", accessToken);

                HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);

                ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-offer/{id}",
                                HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>() {
                                }, 2);

                assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        }

        @Test
        public void select_ReturnsTwo_WhenPageSizeIsTwo() throws IOException {
                ResponseEntity<RestPageImpl<CulturalOfferResponse>> response = restTemplate.exchange(
                                "/api/cultural-offer?size={pageSize}", HttpMethod.GET, null,
                                new ParameterizedTypeReference<>() {
                                }, PAGEABLE_SIZE_TWO);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(1, response.getBody().getTotalPages());
                assertEquals(0, response.getBody().getNumber());
                assertEquals(2, response.getBody().getContent().size());
        }

        @Test
        public void select_ReturnsOne_WhenPageSizeIsOne() {
                ResponseEntity<RestPageImpl<CulturalOfferResponse>> response = restTemplate.exchange(
                                "/api/cultural-offer?page={page}&size={pageSize}", HttpMethod.GET, null,
                                new ParameterizedTypeReference<>() {
                                }, PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_ONE);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().getTotalPages());
                assertEquals(0, response.getBody().getNumber());
                assertEquals(1, response.getBody().getContent().size());
        }

        @Test
        public void select_ReturnsPageTwo_WhenPageIsTwo() {
                ResponseEntity<RestPageImpl<CulturalOfferResponse>> response = restTemplate.exchange(
                                "/api/cultural-offer?page={page}&size={pageSize}", HttpMethod.GET, null,
                                new ParameterizedTypeReference<>() {
                                }, PAGEABLE_PAGE_ONE, PAGEABLE_SIZE_ONE);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().getTotalPages());
                assertEquals(1, response.getBody().getNumber());
                assertEquals(1, response.getBody().getContent().size());
        }

        @Test
        public void selectById_ReturnsResult_WhenResultExists() {
                ResponseEntity<CulturalOfferResponse> response = restTemplate.exchange("/api/cultural-offer/{id}",
                                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_ID_EXISTS);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(CULTURAL_OFFER_ID_EXISTS, response.getBody().getId());
        }

        @Test
        public void selectById_ReturnsNothing_WhenResultNotExists() {
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);

                ResponseEntity<String> response = restTemplate.exchange("/api/cultural-offer/{id}", HttpMethod.GET,
                                httpEntity, new ParameterizedTypeReference<>() {
                                }, CULTURAL_OFFER_ID_NOT_EXISTS);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void selectAll_SelectsSuccessfully() {
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);

                ResponseEntity<List<CulturalOfferResponse>> response = restTemplate.exchange("/api/cultural-offer/all",
                                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
                                });

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().size());
        }
}
