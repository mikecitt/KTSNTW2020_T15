package com.example.culturecontentapp.service;

import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.payload.response.SelectCulturalOfferResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceIntegrationTest {

    @Autowired
    private CulturalOfferService service;

    @Test
    public void searchAndFilter_validParams_hasResults(){
        String offerName = "Exit%";
        String subTypeName = "Festival%";
        String typeName = "Manifestacija%";
        ResponseEntity<List<SelectCulturalOfferResponse>> found = service.searchAndFilter(offerName,subTypeName,typeName);
        assertEquals(1,found.getBody().size());
    }

    @Test
    public void searchAnd_noResultsParams_noResults(){
        String offerName = "BlaBla%";
        String subTypeName = "Festival%";
        String typeName = "Manifestacija%";
        ResponseEntity<List<SelectCulturalOfferResponse>> found = service.searchAndFilter(offerName,subTypeName,typeName);
        assertEquals(0, found.getBody().size());
    }
}
