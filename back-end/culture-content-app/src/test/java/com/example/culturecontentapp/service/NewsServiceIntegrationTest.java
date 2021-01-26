package com.example.culturecontentapp.service;

import static com.example.culturecontentapp.constants.NewsConstants.OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.BAD_OFFER_ID;
import static com.example.culturecontentapp.constants.NewsConstants.BAD_NEWS_ID;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_ID;
import static com.example.culturecontentapp.constants.NewsConstants.NEWS_TIME;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_PAGE;
import static com.example.culturecontentapp.constants.NewsConstants.PAGEABLE_SIZE;
import static com.example.culturecontentapp.constants.NewsConstants.DB_NEWS_SIZE;


import static org.junit.Assert.assertEquals;

import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.NewsNotFoundException;
import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.payload.response.NewsResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceIntegrationTest {
    
    @Autowired
    private NewsService newsService;

    @Test   
    @Transactional
    public void create_validParams_willReturnSucceed(){
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);
        NewsResponse created = newsService.create(newsRequest, OFFER_ID).getBody();
        assertEquals(newsRequest.getText(), created.getText());
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    @Transactional
    public void create_ThrowsCulturalOfferNotFoundException_WhenOfferIdNotExists(){
        newsService.create(new NewsRequest(NEWS, NEWS_TIME), BAD_OFFER_ID);
    }

    @Test
    @Transactional
    public void update_UpdatesSuccessfully(){
        NewsRequest newsRequest = new NewsRequest(NEWS, NEWS_TIME);
        NewsResponse created = newsService.update(newsRequest, NEWS_ID).getBody();
        assertEquals(NEWS, created.getText());
    }

    @Test(expected = NewsNotFoundException.class)
    @Transactional
    public void update_ThrowsNotFoundException_WhenIdNotExists(){
        newsService.update(new NewsRequest(NEWS, NEWS_TIME), BAD_NEWS_ID);
    }

    @Test
    @Transactional
    public void selectByOfferId_SelectsSuccessfully(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        int size = newsService.getOffersNews(OFFER_ID, pageable).getBody().getContent().size();
        assertEquals(DB_NEWS_SIZE, size);
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    @Transactional
    public void selectByOfferId_ThrowsNotFoundException_WhenIdNotExists(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        newsService.getOffersNews(BAD_OFFER_ID, pageable); 
    }

    @Test
    @Transactional
    public void selectById_SelectsSuccessfully(){
        NewsResponse newsResponse = newsService.find(NEWS_ID).getBody();
        assertEquals(NEWS_ID, newsResponse.getId());
    }

    @Test(expected = NewsNotFoundException.class)
    @Transactional
    public void selectById_ThrowsNotFoundException_WhenIdNotExists(){
        newsService.find(BAD_NEWS_ID);
    }

    @Test(expected = NewsNotFoundException.class)
    @Transactional
    public void delete_shouldReturnSuccess(){
        newsService.deleteNews(NEWS_ID);
        newsService.find(NEWS_ID);
    }

    @Test(expected = NewsNotFoundException.class)
    @Transactional
    public void delete_ThrowsNotFoundException_WhenIdNotExists(){
        newsService.deleteNews(BAD_NEWS_ID);
    }

    
}
