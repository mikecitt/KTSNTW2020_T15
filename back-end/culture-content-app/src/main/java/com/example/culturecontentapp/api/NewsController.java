package com.example.culturecontentapp.api;


import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.payload.response.NewsResponse;
import com.example.culturecontentapp.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/news")
@RestController
public class NewsController {

  private final NewsService service;

  @Autowired
  public NewsController(NewsService service) {
    this.service = service;
  }
  
  @PostMapping("/{offerId}")
  public ResponseEntity<NewsResponse> create(@RequestBody NewsRequest newsRequest, @PathVariable Long offerId){
    return new ResponseEntity<>(service.create(newsRequest, offerId), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<NewsResponse> update(@RequestBody NewsRequest newsRequest, @PathVariable Long id){
    return new ResponseEntity<>(service.update(newsRequest, id), HttpStatus.OK);
  }

  @GetMapping("/culturalOffer/{offerId}")
  public ResponseEntity<Page<NewsResponse>> getOffersNews(@PathVariable Long offerId, Pageable pageable){
    return new ResponseEntity<>(service.getOffersNews(offerId, pageable), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<NewsResponse> find(@PathVariable Long id){
    return new ResponseEntity<>(service.find(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNews(@PathVariable Long id){
    service.deleteNews(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
