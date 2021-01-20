package com.example.culturecontentapp.api;

import javax.validation.Valid;

import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.payload.response.NewsResponse;
import com.example.culturecontentapp.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/news")
@RestController
public class NewsController {

  private final NewsService service;

  @Autowired
  public NewsController(NewsService service) {
    this.service = service;
  }
  
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/{offerId}")
  public ResponseEntity<NewsResponse> create(@RequestBody @Valid NewsRequest newsRequest, @PathVariable Long offerId){
    return service.create(newsRequest, offerId);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<NewsResponse> update(@RequestBody @Valid NewsRequest newsRequest, @PathVariable Long id){
    return service.update(newsRequest, id);
  }

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping("/culturalOffer/{offerId}")
  public ResponseEntity<Page<NewsResponse>> getOffersNews(@PathVariable Long offerId, Pageable pageable){
    return service.getOffersNews(offerId, pageable);
  }

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<NewsResponse> find(@PathVariable Long id){
    return service.find(id);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNews(@PathVariable Long id){
    return service.deleteNews(id);
  }


}
