package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.CulturalOfferAlreadyExistsException;
import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.payload.request.CulturalOfferLocationRequest;
import com.example.culturecontentapp.payload.request.CulturalOfferRequest;
import com.example.culturecontentapp.payload.response.CulturalOfferResponse;
import com.example.culturecontentapp.storage.StorageException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_PAGE_ZERO;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_PAGE_ONE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_SIZE_ONE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_SIZE_TWO;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_DESCRIPTION;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION_LONGITUDE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION_ADDRESS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION_LATITUDE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_ID_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_ID_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.STORAGE_FILE_NOT_VALID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceIntegrationTest {

        @Autowired
        private CulturalOfferService service;

        @Test
        public void searchAndFilter_validParams_hasResults() {
                String offerName = "Exit%";
                String subTypeName = "Festival%";
                String typeName = "Manifestacija%";
                ResponseEntity<List<CulturalOfferResponse>> found = service.searchAndFilter(offerName, subTypeName,
                                typeName);
                assertEquals(1, found.getBody().size());
        }

        @Test
        public void searchAnd_noResultsParams_noResults() {
                String offerName = "BlaBla%";
                String subTypeName = "Festival%";
                String typeName = "Manifestacija%";
                ResponseEntity<List<CulturalOfferResponse>> found = service.searchAndFilter(offerName, subTypeName,
                                typeName);
                assertEquals(0, found.getBody().size());
        }

        @Test(expected = CulturalOfferAlreadyExistsException.class)
        public void insert_ThrowsAlreadyExsistsException_WhenNameAlreadyExsits() {
                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);
                service.insert(CULTURAL_OFFER_SUBTYPE, request);
        }

        @Test(expected = SubTypeNotFoundException.class)
        public void insert_ThrowsSubTypeNotFound_WhenSubTypeNotExists() {
                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);
                service.insert(CULTURAL_OFFER_SUBTYPE_NOT_EXISTS, request);
        }

        @Test(expected = StorageException.class)
        public void insert_ThrowsStorageFileInvalid_WhenBase64IsInvalid() {
                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[] { STORAGE_FILE_NOT_VALID });
                service.insert(CULTURAL_OFFER_SUBTYPE, request);
        }

        @Test
        @Transactional
        public void insert_InsertsCulturalOfferSuccessfully() throws IOException {
                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);
                ResponseEntity<CulturalOfferResponse> response = service.insert(CULTURAL_OFFER_SUBTYPE, request);
                assertEquals(request.getName(), response.getBody().getName());
                assertEquals(request.getDescription(), response.getBody().getDescription());
                assertEquals(request.getLocation().getAddress(), response.getBody().getLocation().getAddress());
                assertEquals(request.getLocation().getLongitude(), response.getBody().getLocation().getLongitude());
                assertEquals(request.getLocation().getLatitude(), response.getBody().getLocation().getLatitude());
        }

        @Test(expected = CulturalOfferNotFoundException.class)
        public void update_ThrowsNotFoundException_WhenIdNotExists() {
                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);
                service.update(CULTURAL_OFFER_ID_NOT_EXISTS, request);
        }

        @Test(expected = CulturalOfferAlreadyExistsException.class)
        public void update_ThrowsAlreadyExsitsException_WhenDifferentNameAlreadyExists() {
                CulturalOfferRequest request = new CulturalOfferRequest("Sea Dance festival1",
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);
                service.update(CULTURAL_OFFER_ID_EXISTS, request);
        }

        @Test(expected = StorageException.class)
        public void update_ThrowsStorageFileInvalid_WhenBase64IsInvalid() {
                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[] { STORAGE_FILE_NOT_VALID });
                service.update(CULTURAL_OFFER_ID_EXISTS, request);
        }

        @Test
        @Transactional
        public void update_UpdatesSuccessfully() {
                CulturalOfferRequest request = new CulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION,
                                new CulturalOfferLocationRequest(CULTURAL_OFFER_LOCATION_ADDRESS,
                                                CULTURAL_OFFER_LOCATION_LONGITUDE, CULTURAL_OFFER_LOCATION_LATITUDE),
                                new String[0]);
                ResponseEntity<CulturalOfferResponse> response = service.update(CULTURAL_OFFER_ID_EXISTS, request);
                assertEquals(request.getName(), response.getBody().getName());
                assertEquals(request.getDescription(), response.getBody().getDescription());
                assertEquals(request.getLocation().getAddress(), response.getBody().getLocation().getAddress());
                assertEquals(request.getLocation().getLongitude(), response.getBody().getLocation().getLongitude());
                assertEquals(request.getLocation().getLatitude(), response.getBody().getLocation().getLatitude());
        }

        @Test(expected = CulturalOfferNotFoundException.class)
        public void delete_ThrowsNotFoundException_WhenIdNotExists() {
                service.delete(CULTURAL_OFFER_ID_NOT_EXISTS);
        }

        @Test
        @Transactional
        public void delete_DeletesSuccessfully() {
                ResponseEntity<Void> response = service.delete(CULTURAL_OFFER_ID_EXISTS);
                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }

        @Test
        @Transactional
        public void select_ReturnsTwo_WhenPageSizeIsTwo() {
                Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_TWO);
                ResponseEntity<Page<CulturalOfferResponse>> response = service.select(pageable);
                assertEquals(1, response.getBody().getTotalPages());
                assertEquals(0, response.getBody().getNumber());
                assertEquals(2, response.getBody().getContent().size());
        }

        @Test
        @Transactional
        public void select_ReturnsOne_WhenPageSizeIsOne() {
                Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_ONE);
                ResponseEntity<Page<CulturalOfferResponse>> response = service.select(pageable);
                assertEquals(2, response.getBody().getTotalPages());
                assertEquals(0, response.getBody().getNumber());
                assertEquals(1, response.getBody().getContent().size());
        }

        @Test
        @Transactional
        public void select_ReturnsPageTwo_WhenPageIsTwo() {
                Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ONE, PAGEABLE_SIZE_ONE);
                ResponseEntity<Page<CulturalOfferResponse>> response = service.select(pageable);
                assertEquals(2, response.getBody().getTotalPages());
                assertEquals(1, response.getBody().getNumber());
                assertEquals(1, response.getBody().getContent().size());
        }

        @Test(expected = CulturalOfferNotFoundException.class)
        public void selectById_ThrowsNotFoundException_WhenIdNotExists() {
                service.selectById(CULTURAL_OFFER_ID_NOT_EXISTS);
        }

        @Test
        @Transactional
        public void selectById_SelectsSuccessfully() {
                ResponseEntity<CulturalOfferResponse> response = service.selectById(CULTURAL_OFFER_ID_EXISTS);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(CULTURAL_OFFER_ID_EXISTS, response.getBody().getId());
        }

        @Test
        @Transactional
        public void selectAll_SelectsSuccessfully() {
                ResponseEntity<List<CulturalOfferResponse>> response = service.selectAll();
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().size());
        }

}
