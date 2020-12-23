package com.example.culturecontentapp.service;

import com.example.culturecontentapp.model.Type;
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

import java.util.Optional;

import static com.example.culturecontentapp.constants.TypeConstants.*;
import static org.junit.Assert.assertEquals;

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
    public void testCreate(){
        TypeRequest newType = new TypeRequest(NEW_TYPE);
        TypeResponse createdType = typeService.create(newType);

        assertEquals(NEW_TYPE, createdType.getName());

//        //obrisemo dodati
//        Optional<Type> type = typeRepository.findById(createdType.getId());
//        typeRepository.delete(type.get());
    }

    @Test
    public void testUpdate(){
        TypeRequest newType = new TypeRequest(NEW_TYPE);
        TypeResponse updatedType = typeService.update(newType, DB_TYPE_ID);

        assertEquals(NEW_TYPE, updatedType.getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete(){
        long BEFORE_DELETING = typeRepository.count();
        typeService.delete(DB_TYPE_WITHOUT_SUBTYPE_ID);
        assertEquals(BEFORE_DELETING - 1, typeRepository.count());
    }
}
