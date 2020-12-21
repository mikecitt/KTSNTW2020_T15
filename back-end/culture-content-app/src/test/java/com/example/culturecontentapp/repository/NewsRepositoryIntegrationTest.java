package com.example.culturecontentapp.repository;

import static com.example.culturecontentapp.constants.NewsConstants.CATEGORY_ID;
import static com.example.culturecontentapp.constants.NewsConstants.DB_NEWS_SIZE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_PAGE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_SIZE;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.example.culturecontentapp.model.News;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsRepositoryIntegrationTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void testFindByCulturalOffer() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        List<News> found = newsRepository.findByCulturalOffer(CATEGORY_ID,pageable);
        assertEquals(DB_NEWS_SIZE, found.size());
    }
    
}
