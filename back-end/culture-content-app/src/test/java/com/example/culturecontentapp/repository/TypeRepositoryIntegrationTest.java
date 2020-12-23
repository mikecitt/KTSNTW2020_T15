package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.example.culturecontentapp.constants.TypeConstants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class TypeRepositoryIntegrationTest {

    @Autowired
    private TypeRepository typeRepository;

    @Test
    public void testFindByName(){
        Type culturalContentType = typeRepository.findByName(DB_TYPE);
        assertEquals(DB_TYPE, culturalContentType.getName());
    }
}
