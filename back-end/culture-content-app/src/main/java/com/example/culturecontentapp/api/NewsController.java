package com.example.culturecontentapp.api;

import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  
  @PostMapping("")
  public void addNews(@RequestBody NewsRequest newsRequest){
    service.addNews(newsRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteNews(@PathVariable Long id){
    service.deleteNews(id);
  }


}
