package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.TypeAlreadyExistsException;
import com.example.culturecontentapp.exception.TypeNotFoundException;
import com.example.culturecontentapp.payload.request.TypeRequest;
import com.example.culturecontentapp.payload.response.TypeResponse;
import com.example.culturecontentapp.repository.TypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.culturecontentapp.constants.TypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class TypeServiceIntegrationTest {

    @Autowired
    private TypeService typeService;

    @Autowired
    private TypeRepository typeRepository;

    @Test
    public void testFindAll(){
        List<TypeResponse> types = typeService.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, types.size());
    }

    @Test
    public void testFindAllPaginated(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<TypeResponse> foundTypes = typeService.findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, foundTypes.getSize());
    }

    @Test
    public void testFindById(){
        TypeResponse foundType = typeService.findById(DB_TYPE_ID);
        assertEquals(DB_TYPE_ID, foundType.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void create_validParams_willReturnSucceed(){
        TypeRequest newType = new TypeRequest(NEW_TYPE);
        TypeResponse createdType = typeService.create(newType);

        assertEquals(NEW_TYPE, createdType.getName());
    }
    @Test(expected = TypeAlreadyExistsException.class)
    @Transactional
    @Rollback(true)
    public void create_badParams_willReturnAlreadyExists(){
        TypeRequest newType = new TypeRequest(DB_TYPE);
        typeService.create(newType);

        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, typeRepository.count());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void update_validParams_willReturnSucceed(){
        TypeRequest newType = new TypeRequest(NEW_TYPE);
        TypeResponse updatedType = typeService.update(newType, DB_TYPE_ID);

        assertEquals(NEW_TYPE, updatedType.getName());
    }

    @Test(expected = TypeAlreadyExistsException.class)
    @Transactional
    @Rollback(true)
    public void update_badParams_willReturnAlreadyExists(){
        TypeRequest newType = new TypeRequest(DB_TYPE_WITHOUT_SUBTYPE);
        TypeResponse updatedType = typeService.update(newType, DB_TYPE_ID);

        assertNotEquals(DB_TYPE_WITHOUT_SUBTYPE, updatedType.getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void delete_validParams_willReturnSucceed(){
        long BEFORE_DELETING = typeRepository.count();
        typeService.delete(DB_TYPE_WITHOUT_SUBTYPE_ID);
        assertEquals(BEFORE_DELETING - 1, typeRepository.count());
    }

    @Test(expected = TypeNotFoundException.class)
    @Transactional
    @Rollback(true)
    public void delete_badParams_willReturnNotFound(){
        long BEFORE_DELETING = typeRepository.count();
        typeService.delete(TYPE_DOESNT_EXISTS);
        assertEquals(BEFORE_DELETING, typeRepository.count());
    }
}
