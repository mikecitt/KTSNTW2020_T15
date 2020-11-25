package com.example.culturecontentapp.api;

import com.example.culturecontentapp.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

  private final ReviewService service;

  @Autowired
  public ReviewController(ReviewService service) {
    this.service = service;
  }
}
