package com.example.culturecontentapp.api;

import com.example.culturecontentapp.payload.response.ReviewResponse;
import com.example.culturecontentapp.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

  private final ReviewService service;

  @Autowired
  public ReviewController(ReviewService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Page<ReviewResponse>> get(@PathVariable Long id, Pageable pageable) {
    return this.service.get(id, pageable);
  }
}
