package com.example.culturecontentapp.api;

import javax.validation.Valid;

import com.example.culturecontentapp.payload.request.AddReviewRequest;
import com.example.culturecontentapp.payload.response.AddReviewResponse;
import com.example.culturecontentapp.payload.response.ReviewResponse;
import com.example.culturecontentapp.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

  @PostMapping
  public ResponseEntity<AddReviewResponse> addReview(@RequestParam Long culturalOfferId,
      @RequestPart("request") @Valid AddReviewRequest request, @RequestPart("files") MultipartFile[] files) {
    return this.service.addReview(culturalOfferId, request, files);
  }
}
