package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.NewsNotFoundException;
import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.model.News;
import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.payload.response.NewsResponse;
import com.example.culturecontentapp.repository.CulturalOfferRepository;
import com.example.culturecontentapp.repository.NewsRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

  private final NewsRepository repository;
  private final CulturalOfferRepository offerRepository;

  private final ModelMapper mapper;

  @Autowired
  public NewsService(NewsRepository repository, CulturalOfferRepository offerRepository, ModelMapper mapper) {
    this.repository = repository;
    this.offerRepository = offerRepository;
    this.mapper = mapper;
  }

  public NewsResponse create(NewsRequest newsRequest, Long offerId){
    CulturalOffer offer = offerRepository.findById(offerId).orElseThrow(() -> new CulturalOfferNotFoundException("Cultural offer doesn't exist"));
    News news = mapper.map(newsRequest, News.class);
    offer.addNews(news);

    return convertToNewsResponse(repository.save(news));
    
  }

  public void deleteNews(Long id) {
    News news = repository.findById(id).orElseThrow(()-> new NewsNotFoundException("The news you want to delete doesn't exist."));
    repository.delete(news);

  }

  private NewsResponse convertToNewsResponse(News news){
    return mapper.map(news, NewsResponse.class);
  }
}
