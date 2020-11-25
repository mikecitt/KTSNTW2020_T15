package com.example.culturecontentapp.service;

import com.example.culturecontentapp.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final ReviewRepository repository;

  @Autowired
  public ReviewService(ReviewRepository repository) {
    this.repository = repository;
  }
}
