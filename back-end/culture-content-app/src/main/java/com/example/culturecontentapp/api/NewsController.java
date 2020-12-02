package com.example.culturecontentapp.api;

import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.payload.response.NewsResponse;
import com.example.culturecontentapp.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/news")
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

  @DeleteMapping("/{id}")
  public void deleteNews(@PathVariable Long id){
    service.deleteNews(id);
  }


}
