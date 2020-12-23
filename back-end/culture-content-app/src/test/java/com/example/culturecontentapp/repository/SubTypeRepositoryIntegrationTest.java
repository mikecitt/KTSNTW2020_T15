package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.model.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.example.culturecontentapp.constants.SubTypeConstants.*;
import static com.example.culturecontentapp.constants.TypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubTypeRepositoryIntegrationTest {

    @Autowired
    private SubTypeRepository repository;

    @Test
    public void testFindAllByTypeId(){
        List<SubType> subTypeList = repository.findAllByTypeId(DB_TYPE_ID, PageRequest.of(SB_PAGEABLE_PAGE,SB_PAGEABLE_SIZE));
        assertEquals(SB_FIND_ALL_NUMBER_OF_ITEMS,subTypeList.size());
    }

    @Test
    public void testFindByIdAndTypeId(){
        SubType subType = repository.findByIdAndTypeId(DB_TYPE_ID, DB_SUBTYPE_ID);
        assertEquals(DB_SUBTYPE_NAME,subType.getName());
    }

    @Test
    public void testFindByName(){
        SubType subType = repository.findByName(DB_SUBTYPE_NAME);
        assertEquals(DB_SUBTYPE_NAME, subType.getName());
    }

    @Test
    public void testFindByNameAndIdNot(){
        SubType subType = repository.findByNameAndIdNot(DB_SUBTYPE_NAME, DB_SUBTYPE_ID);
        assertNull(subType);
    }
}
