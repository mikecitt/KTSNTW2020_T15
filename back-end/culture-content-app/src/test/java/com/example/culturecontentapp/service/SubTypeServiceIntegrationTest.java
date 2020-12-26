package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.SubTypeAlreadyExistsException;
import com.example.culturecontentapp.exception.SubTypeHasCulturalOffersException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.exception.TypeAlreadyExistsException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Optional;

import static com.example.culturecontentapp.constants.SubTypeConstants.*;
import static com.example.culturecontentapp.constants.TypeConstants.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubTypeServiceIntegrationTest {

    @Autowired
    private SubTypeService service;
    @Autowired
    private SubTypeRepository repository;

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
    @Transactional
    @Rollback(true)
    public void create_validParams_willReturnSucceed(){
        long BEFORE_CREATE = repository.countAllByTypeId(DB_TYPE_ID);
        SubTypeRequest newTypeRequest = new SubTypeRequest(NEW_SUBTYPE_NAME);
        SubTypeResponse createdSubType = service.create(newTypeRequest,DB_TYPE_ID);

        Long typeId = createdSubType.getTypeId();
        assertEquals(NEW_SUBTYPE_NAME, createdSubType.getName());
        assertEquals(DB_TYPE_ID, typeId);
        assertEquals(BEFORE_CREATE + 1, (long) repository.countAllByTypeId(typeId));

    }

    @Test(expected = SubTypeAlreadyExistsException.class)
    @Transactional
    @Rollback(true)
    public void create_nameAlreadyExists_willReturnAlreadyExists(){
        long BEFORE_CREATE = repository.countAllByTypeId(DB_TYPE_ID);
        SubTypeRequest newTypeRequest = new SubTypeRequest(DB_SUBTYPE_NAME);
        SubTypeResponse createdSubType = service.create(newTypeRequest,DB_TYPE_ID);

        assertNull(createdSubType);
        assertEquals(BEFORE_CREATE, (long) repository.countAllByTypeId(DB_TYPE_ID));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void update_validParams_willReturnSucceed(){
        SubTypeRequest newTypeRequest = new SubTypeRequest(NEW_SUBTYPE_NAME);
        SubTypeResponse updated = service.update(newTypeRequest,DB_TYPE_ID,DB_SUBTYPE_ID);

        assertEquals(NEW_SUBTYPE_NAME, updated.getName());

    }

    @Test(expected = SubTypeAlreadyExistsException.class)
    @Transactional
    @Rollback(true)
    public void update_nameAlreadyExists_willReturnAlreadyExists(){
        SubTypeRequest newTypeRequest = new SubTypeRequest(DB_SUBTYPE_WITHOUT_CULTURAL_OFFER_NAME);
        SubTypeResponse updated = service.update(newTypeRequest,DB_TYPE_ID,DB_SUBTYPE_ID);

        assertNotEquals(newTypeRequest.getName(),updated.getName());
    }

    @Test(expected = SubTypeNotFoundException.class)
    @Transactional
    @Rollback(true)
    public void update_idDoesntExists_willReturnNotFound(){
        SubTypeRequest newTypeRequest = new SubTypeRequest(NEW_SUBTYPE_NAME);
        SubTypeResponse updated = service.update(newTypeRequest,DB_TYPE_ID,SUBTYPE_DOESNT_EXIST_ID);

        assertNull(updated);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete(){
        long BEFORE_DELETING = repository.countAllByTypeId(DB_TYPE_ID);
        service.delete(DB_TYPE_ID, SUBTYPE_ID_WITHOUT_CULTURAL_OFFER);
        long size = repository.countAllByTypeId(DB_TYPE_ID);
        assertEquals(BEFORE_DELETING-1, size);
        SubType type = repository.findByIdAndTypeId(SUBTYPE_ID_WITHOUT_CULTURAL_OFFER, DB_TYPE_ID);
        assertNull(type);
    }

    @Test(expected = SubTypeHasCulturalOffersException.class)
    @Transactional
    @Rollback(true)
    public void testDeleteSubTypeHasCulturalOfferWillReturnException(){
        long BEFORE_DELETING = repository.countAllByTypeId(DB_TYPE_ID);
        service.delete(DB_TYPE_ID, DB_SUBTYPE_ID);
        long size = repository.countAllByTypeId(DB_TYPE_ID);
        assertEquals(BEFORE_DELETING-1, size);

    }

}



