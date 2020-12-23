package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.model.Type;
import com.example.culturecontentapp.payload.request.SubTypeRequest;
import com.example.culturecontentapp.payload.request.TypeRequest;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.repository.SubTypeRepository;
import com.example.culturecontentapp.repository.TypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Optional;

import static com.example.culturecontentapp.constants.SubTypeConstants.*;
import static com.example.culturecontentapp.constants.TypeConstants.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubTypeServiceIntegrationTest {

    @Autowired
    private SubTypeService service;
    @Autowired
    private SubTypeRepository repository;
    @Autowired
    private TypeRepository typeRepository;

    @Test
    public void testFindAll(){
        Pageable pageable = PageRequest.of(SB_PAGEABLE_PAGE,SB_PAGEABLE_SIZE);
        Page<SubTypeResponse> foundSub = service.findAll(DB_TYPE_ID,pageable);
        assertEquals(SB_FIND_ALL_NUMBER_OF_ITEMS, foundSub.getSize());
    }

    @Test
    public void testFindOne(){
        SubTypeResponse response = service.findOne(DB_TYPE_ID, DB_SUBTYPE_ID);
        assertEquals(DB_SUBTYPE_NAME,response.getName());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreate(){
        SubTypeRequest newTypeRequest = new SubTypeRequest(NEW_SUBTYPE_NAME);
        SubTypeResponse createdSubType = service.create(newTypeRequest,DB_TYPE_ID);

        Long typeId = createdSubType.getTypeId();
        assertEquals(NEW_SUBTYPE_NAME, createdSubType.getName());
        assertEquals(DB_TYPE_ID, typeId);

        SubType subType = repository.findByIdAndTypeId(createdSubType.getId(),typeId);
        repository.delete(subType);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdate(){
        SubTypeRequest newTypeRequest = new SubTypeRequest(NEW_SUBTYPE_NAME);
        String odlSubTypeName = repository.findNameByIdAndTypeId(DB_SUBTYPE_ID,DB_TYPE_ID);
        SubTypeResponse updated = service.update(newTypeRequest,DB_TYPE_ID,DB_SUBTYPE_ID);

        assertEquals(NEW_SUBTYPE_NAME, updated.getName());

        //vratimo vrednost
        SubType subType = repository.findByIdAndTypeId(DB_SUBTYPE_ID, DB_TYPE_ID);
        subType.update(odlSubTypeName);
        repository.save(subType);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDelete(){
        long BEFORE_DELETING = repository.countAllByTypeId(DB_TYPE_ID);
        service.delete(DB_TYPE_ID, DB_SUBTYPE_ID);
        long size = repository.countAllByTypeId(DB_TYPE_ID);
        assertEquals(BEFORE_DELETING-1, size);

    }

}



