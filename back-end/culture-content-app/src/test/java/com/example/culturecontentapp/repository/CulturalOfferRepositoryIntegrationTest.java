package com.example.culturecontentapp.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import com.example.culturecontentapp.model.CulturalOffer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_PAGE_ZERO;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_PAGE_ONE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_SIZE_ONE;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.PAGEABLE_SIZE_TWO;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_EXISTS;
import static com.example.culturecontentapp.constants.CulturalOfferConstants.CULTURAL_OFFER_NAME_NOT_EXISTS;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferRepositoryIntegrationTest {

    @Autowired
    private CulturalOfferRepository repository;

    @Test
    public void findByFilterCriteria_validParams_hasResults() {
        String offerName = "Exit%";
        String subTypeName = "Festival%";
        String typeName = "Manifestacija%";
        List<CulturalOffer> found = repository.FindByFilterCriteria(offerName, subTypeName, typeName);
        assertEquals(1, found.size());
    }

    @Test
    public void findByFilterCriteria_validParams_noResults() {
        String offerName = "Kulturna ponuda%";
        String subTypeName = "Festival%";
        String typeName = "Manifestacija%";
        List<CulturalOffer> found = repository.FindByFilterCriteria(offerName, subTypeName, typeName);
        assertEquals(0, found.size());
    }

    @Test
    public void findAll_ReturnsTwo_WhenPageSizeIsTwo() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_TWO);
        Page<CulturalOffer> culturalOffers = repository.findAll(pageable);
        assertEquals(1, culturalOffers.getTotalPages());
        assertEquals(0, culturalOffers.getNumber());
        assertEquals(2, culturalOffers.getContent().size());
    }

    @Test
    public void findAll_ReturnsOne_WhenPageSizeIsOne() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_ONE);
        Page<CulturalOffer> culturalOffers = repository.findAll(pageable);
        assertEquals(2, culturalOffers.getTotalPages());
        assertEquals(0, culturalOffers.getNumber());
        assertEquals(1, culturalOffers.getContent().size());
    }

    @Test
    public void findAll_ReturnsPageTwo_WhenPageIsTwo() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ONE, PAGEABLE_SIZE_ONE);
        Page<CulturalOffer> culturalOffers = repository.findAll(pageable);
        assertEquals(2, culturalOffers.getTotalPages());
        assertEquals(1, culturalOffers.getNumber());
        assertEquals(1, culturalOffers.getContent().size());
    }

    @Test
    public void findByName_ReturnsResult_WhenResultExists() {
        Optional<CulturalOffer> culturalOffer = repository.findByName(CULTURAL_OFFER_NAME_EXISTS);
        assertEquals(true, culturalOffer.isPresent());
    }

    @Test
    public void findByName_ReturnsNothing_WhenResultNotExists() {
        Optional<CulturalOffer> culturalOffer = repository.findByName(CULTURAL_OFFER_NAME_NOT_EXISTS);
        assertEquals(false, culturalOffer.isPresent());
    }
}
