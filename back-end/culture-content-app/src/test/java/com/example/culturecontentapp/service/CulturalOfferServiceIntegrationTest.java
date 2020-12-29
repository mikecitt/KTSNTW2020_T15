package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.CulturalOfferAlreadyExistsException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.payload.request.NewCulturalOfferRequest;
import com.example.culturecontentapp.payload.response.NewCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.SelectCulturalOfferResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.Assert.assertEquals;

import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_NOT_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_DESCRIPTION;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_LOCATION;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_SUBTYPE_NOT_EXISTS;

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
                service.insert(CULTURAL_OFFER_SUBTYPE, request, new MultipartFile[0]);
        }

        @Test(expected = SubTypeNotFoundException.class)
        public void insert_ThrowsSubTypeNotFound_WhenSubTypeNotExists() {
                NewCulturalOfferRequest request = new NewCulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);
                service.insert(CULTURAL_OFFER_SUBTYPE_NOT_EXISTS, request, new MultipartFile[0]);
        }

        @Test
        public void insert_InsertsCulturalOfferSuccessfully() {
                NewCulturalOfferRequest request = new NewCulturalOfferRequest(CULTURAL_OFFER_NAME_NOT_EXISTS,
                                CULTURAL_OFFER_DESCRIPTION, CULTURAL_OFFER_LOCATION);
                ResponseEntity<NewCulturalOfferResponse> response = service.insert(CULTURAL_OFFER_SUBTYPE, request,
                                new MultipartFile[0]);
                assertEquals(request.getName(), response.getBody().getName());
                assertEquals(request.getDescription(), response.getBody().getDescription());
                assertEquals(request.getLocation(), response.getBody().getLocation());
        }
}
