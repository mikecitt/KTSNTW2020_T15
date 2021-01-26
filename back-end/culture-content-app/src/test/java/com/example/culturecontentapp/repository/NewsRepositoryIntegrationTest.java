package com.example.culturecontentapp.repository;

import static com.example.culturecontentapp.constants.NewsConstants.OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.DB_NEWS_SIZE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_PAGE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_SIZE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_SIZE_ONE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_SIZE_TWO;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_PAGE_ONE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_PAGE_ZERO;
import static org.junit.Assert.assertEquals;


import com.example.culturecontentapp.model.News;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
    public void findByCulturalOffer_validParams_shouldSucceed() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<News> found = newsRepository.findByCulturalOffer(OFFER_ID,pageable);
        assertEquals(DB_NEWS_SIZE, found.getContent().size());
    }

    @Test
    public void findByCulturalOffer_ReturnsTwo_WhenPageSizeTwo(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ZERO, PAGEABLE_SIZE_TWO);
        Page<News> found = newsRepository.findByCulturalOffer(OFFER_ID,pageable);
        assertEquals(1, found.getTotalPages());
        assertEquals(0, found.getNumber());
        assertEquals(2, found.getContent().size());

    }

    @Test
    public void findByCulturalOffer_ReturnsPageTwo_WhenPageIsTwo() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE_ONE, PAGEABLE_SIZE_ONE);
        Page<News> news = newsRepository.findAll(pageable);
        assertEquals(2, news.getTotalPages());
        assertEquals(1, news.getNumber());
        assertEquals(1, news.getContent().size());
    }
    
}
