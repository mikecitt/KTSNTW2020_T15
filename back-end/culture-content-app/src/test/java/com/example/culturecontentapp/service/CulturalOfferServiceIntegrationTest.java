package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.CulturalOfferAlreadyExistsException;
import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.payload.request.EditCulturalOfferRequest;
import com.example.culturecontentapp.payload.request.NewCulturalOfferRequest;
import com.example.culturecontentapp.payload.response.EditCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.NewCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.SelectCulturalOfferResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;
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
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_ID_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_ID_EXISTS;

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
                ResponseEntity<List<SelectCulturalOfferResponse>> found = service.searchAndFilter(offerName,
                                subTypeName, typeName);
                assertEquals(1, found.getBody().size());
        }

        @Test
        public void searchAnd_noResultsParams_noResults() {
                String offerName = "BlaBla%";
                String subTypeName = "Festival%";
                String typeName = "Manifestacija%";
                ResponseEntity<List<SelectCulturalOfferResponse>> found = service.searchAndFilter(offerName,
                                subTypeName, typeName);
                assertEquals(0, found.getBody().size());
        }

        @Test(expected = CulturalOfferAlreadyExistsException.class)
        public void insert_ThrowsAlreadyExsistsException_WhenNameAlreadyExsits() {
                NewCulturalOfferRequest request = new NewCulturalOfferRequest(CULTURAL_OFFER_NAME_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);
                service.insert(CULTURAL_OFFER_SUBTYPE, request);
        }

        @Test(expected = SubTypeNotFoundException.class)
        public void insert_ThrowsSubTypeNotFound_WhenSubTypeNotExists() {
                NewCulturalOfferRequest request = new NewCulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);
                service.insert(CULTURAL_OFFER_SUBTYPE_NOT_EXISTS, request);
        }

        @Test
        @Transactional
        public void insert_InsertsCulturalOfferSuccessfully() {
                NewCulturalOfferRequest request = new NewCulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);
                ResponseEntity<NewCulturalOfferResponse> response = service.insert(CULTURAL_OFFER_SUBTYPE, request);
                assertEquals(request.getName(), response.getBody().getName());
                assertEquals(request.getDescription(), response.getBody().getDescription());
                assertEquals(request.getLocation(), response.getBody().getLocation());
        }

        @Test(expected = CulturalOfferNotFoundException.class)
        public void update_ThrowsNotFoundException_WhenIdNotExists() {
                EditCulturalOfferRequest request = new EditCulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);
                service.update(CULTURAL_OFFER_ID_NOT_EXISTS, request);
        }

        @Test(expected = CulturalOfferAlreadyExistsException.class)
        public void update_ThrowsAlreadyExsitsException_WhenDifferentNameAlreadyExists() {
                EditCulturalOfferRequest request = new EditCulturalOfferRequest("Sea Dance festival1",
                                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);
                service.update(CULTURAL_OFFER_ID_EXISTS, request);
        }

        @Test
        @Transactional
        public void update_UpdatesSuccessfully() {
                EditCulturalOfferRequest request = new EditCulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);
                ResponseEntity<EditCulturalOfferResponse> response = service.update(CULTURAL_OFFER_ID_EXISTS, request);
                assertEquals(request.getName(), response.getBody().getName());
                assertEquals(request.getDescription(), response.getBody().getDescription());
                assertEquals(request.getLocation(), response.getBody().getLocation());
        }

        @Test(expected = CulturalOfferNotFoundException.class)
        public void delete_ThrowsNotFoundException_WhenIdNotExists() {
                service.delete(CULTURAL_OFFER_ID_NOT_EXISTS);
        }

        @Test
        @Transactional
        public void select_ReturnsTwo_WhenPageSizeIsTwo() {
                Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_TWO);
                ResponseEntity<Page<SelectCulturalOfferResponse>> response = service.select(pageable);
                assertEquals(1, response.getBody().getTotalPages());
                assertEquals(0, response.getBody().getNumber());
                assertEquals(2, response.getBody().getContent().size());
        }

        @Test
        @Transactional
        public void select_ReturnsOne_WhenPageSizeIsOne() {
                Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_ONE);
                ResponseEntity<Page<SelectCulturalOfferResponse>> response = service.select(pageable);
                assertEquals(2, response.getBody().getTotalPages());
                assertEquals(0, response.getBody().getNumber());
                assertEquals(1, response.getBody().getContent().size());
        }

        @Test
        @Transactional
        public void select_ReturnsPageTwo_WhenPageIsTwo() {
                Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ONE, PAGEABLE_SIZE_ONE);
                ResponseEntity<Page<SelectCulturalOfferResponse>> response = service.select(pageable);
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
                ResponseEntity<SelectCulturalOfferResponse> response = service.selectById(CULTURAL_OFFER_ID_EXISTS);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(CULTURAL_OFFER_ID_EXISTS, response.getBody().getId());
        }
}
