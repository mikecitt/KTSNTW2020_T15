package com.example.culturecontentapp.api;

import com.example.culturecontentapp.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

  private final NewsService service;

  @Autowired
  public NewsController(NewsService service) {
    this.service = service;
  }
}
