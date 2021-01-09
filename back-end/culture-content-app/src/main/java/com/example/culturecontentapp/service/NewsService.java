package com.example.culturecontentapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.culturecontentapp.event.OnNewsCreatedEvent;
import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.NewsNotFoundException;
import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.model.News;
import com.example.culturecontentapp.model.User;
import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.payload.response.NewsResponse;
import com.example.culturecontentapp.repository.CulturalOfferRepository;
import com.example.culturecontentapp.repository.NewsRepository;
import com.example.culturecontentapp.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

  private final NewsRepository repository;
  private final CulturalOfferRepository offerRepository;
  private final UserRepository userRepository;
  ApplicationEventPublisher eventPublisher;
  private final ModelMapper mapper;
//
  @Autowired
  public NewsService(NewsRepository repository, CulturalOfferRepository offerRepository, UserRepository userRepository, ModelMapper mapper, ApplicationEventPublisher eventPublisher) {
    this.repository = repository;
    this.offerRepository = offerRepository;
    this.userRepository = userRepository;
    this.mapper = mapper;
    this.eventPublisher = eventPublisher;
  }

  public ResponseEntity<NewsResponse> create(NewsRequest newsRequest, Long offerId){
    CulturalOffer offer = offerRepository.findById(offerId).orElseThrow(() -> new CulturalOfferNotFoundException("Cultural offer doesn't exist"));
    News news = mapper.map(newsRequest, News.class);
    offer.addNews(news);
    handlePublishingNewsEvent(offerId, news, offer.getName());
    return new ResponseEntity<>(convertToNewsResponse(repository.save(news)), HttpStatus.CREATED);
    
  }

  private void handlePublishingNewsEvent(Long offerId, News news, String offerName){
    List<User> users = userRepository.findByCulturalOffersId(offerId);
    ArrayList<String> userEmails = new ArrayList<String>();
    for(User u : users){
      userEmails.add(u.getEmail());
    }
    eventPublisher.publishEvent(new OnNewsCreatedEvent(news, offerName, userEmails));
  }

  public ResponseEntity<NewsResponse> update(NewsRequest newsRequest, Long id){
    News news = repository.findById(id).orElseThrow(()-> new NewsNotFoundException("The news you want to update doesn't exist."));
    news.setDate(newsRequest.getDate());
    news.setText(newsRequest.getText());
    news.setImages(newsRequest.getImages());
    return new ResponseEntity<>(convertToNewsResponse(repository.save(news)), HttpStatus.OK);
  }

  public ResponseEntity<Void> deleteNews(Long id) {
    News news = repository.findById(id).orElseThrow(()-> new NewsNotFoundException("The news you want to delete doesn't exist."));
    repository.delete(news);
    return new ResponseEntity<>(HttpStatus.OK);

  }

  private NewsResponse convertToNewsResponse(News news){
    return mapper.map(news, NewsResponse.class);
  }

  public Page<NewsResponse> toPageNewsResponse(Page<News> news) {
    return news.map(this::convertToNewsResponse);
}

  public ResponseEntity<Page<NewsResponse>> getOffersNews(Long offerId, Pageable pageable) {
    offerRepository.findById(offerId).orElseThrow(() -> new CulturalOfferNotFoundException("Cultural offer doesn't exist"));
    Page<News> news = repository.findByCulturalOffer(offerId, pageable);
    return new ResponseEntity<>(toPageNewsResponse(news), HttpStatus.OK);
  }

  public ResponseEntity<NewsResponse> find(Long id){
    News news = repository.findById(id).orElseThrow(()-> new NewsNotFoundException("The news doesn't exist."));
    return new ResponseEntity<>(convertToNewsResponse(news), HttpStatus.OK);
  }
}
