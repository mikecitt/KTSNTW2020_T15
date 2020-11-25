package com.example.culturecontentapp.service;

import com.example.culturecontentapp.repository.NewsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

  private final NewsRepository repository;

  @Autowired
  public NewsService(NewsRepository repository) {
    this.repository = repository;
  }
}
