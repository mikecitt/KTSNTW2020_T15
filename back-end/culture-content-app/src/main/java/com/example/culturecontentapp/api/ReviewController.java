package com.example.culturecontentapp.api;

import javax.validation.Valid;

import com.example.culturecontentapp.payload.request.ReviewRequest;
import com.example.culturecontentapp.payload.response.ReviewResponse;
import com.example.culturecontentapp.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

  private final ReviewService service;

  @Autowired
  public ReviewController(ReviewService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<ReviewResponse>> get(@RequestParam Long culturalOfferId, Pageable pageable) {
    return this.service.get(culturalOfferId, pageable);
  }

  @PreAuthorize("hasRole('ROLE_USER')")
  @PostMapping
  public ResponseEntity<ReviewResponse> addReview(@RequestParam Long culturalOfferId,
      @RequestBody @Valid ReviewRequest request) {
    return this.service.addReview(culturalOfferId, request);
  }
}
