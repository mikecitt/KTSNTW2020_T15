package com.example.culturecontentapp.service;

import com.example.culturecontentapp.model.Review;
import com.example.culturecontentapp.payload.response.ReviewResponse;
import com.example.culturecontentapp.repository.ReviewRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final ReviewRepository repository;
  private final ModelMapper modelMapper;

  @Autowired
  public ReviewService(ReviewRepository repository, ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
  }

  public ResponseEntity<Page<ReviewResponse>> get(Long id, Pageable pageable) {
    Page<Review> reviews = repository.findByCulturalOffer(id, pageable);

    return new ResponseEntity<>(reviews.map(review -> modelMapper.map(review, ReviewResponse.class)), HttpStatus.OK);
  }
}
