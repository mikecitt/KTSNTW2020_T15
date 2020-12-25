package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.CulturalOffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferRepositoryIntegrationTest {

    @Autowired
    private CulturalOfferRepository repository;

    @Test
    public void findByFilterCriteria_validParams_hasResults(){
        String offerName = "Exit%";
        String subTypeName = "Festival%";
        String typeName = "Manifestacija%";
        List<CulturalOffer> found = repository.FindByFilterCriteria(offerName,subTypeName,typeName);
        assertEquals(1,found.size());
    }

    @Test
    public void findByFilterCriteria_validParams_noResults(){
        String offerName = "Kulturna ponuda%";
        String subTypeName = "Festival%";
        String typeName = "Manifestacija%";
        List<CulturalOffer> found = repository.FindByFilterCriteria(offerName,subTypeName,typeName);
        assertEquals(0,found.size());
    }
}
