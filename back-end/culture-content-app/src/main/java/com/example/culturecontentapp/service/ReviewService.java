package com.example.culturecontentapp.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.model.Review;
import com.example.culturecontentapp.payload.request.AddReviewRequest;
import com.example.culturecontentapp.payload.response.AddReviewResponse;
import com.example.culturecontentapp.payload.response.ReviewResponse;
import com.example.culturecontentapp.repository.CulturalOfferRepository;
import com.example.culturecontentapp.repository.ReviewRepository;
import com.example.culturecontentapp.storage.StorageService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReviewService {

  private final ReviewRepository repository;
  private final CulturalOfferRepository culturalOfferRepository;
  private final ModelMapper modelMapper;
  private final StorageService storageService;

  @Autowired
  public ReviewService(ReviewRepository repository, CulturalOfferRepository culturalOfferRepository,
      StorageService storageService, ModelMapper modelMapper) {
    this.repository = repository;
    this.culturalOfferRepository = culturalOfferRepository;
    this.storageService = storageService;
    this.modelMapper = modelMapper;
  }

  public ResponseEntity<Page<ReviewResponse>> get(Long culturalOfferId, Pageable pageable) {
    Page<Review> reviews = repository.findByCulturalOffer(culturalOfferId, pageable);

    return new ResponseEntity<>(reviews.map(review -> modelMapper.map(review, ReviewResponse.class)), HttpStatus.OK);
  }

  public ResponseEntity<AddReviewResponse> addReview(Long culturalOfferId, AddReviewRequest request,
      MultipartFile[] files) {

    Optional<CulturalOffer> culturalOfferEntity = this.culturalOfferRepository.findById(culturalOfferId);
    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException("Cultural offer with the given id not exists");
    }

    Set<String> fileNames = new HashSet<>();
    if (files.length >= 1 && !files[0].isEmpty()) {
      fileNames = Arrays.asList(files).stream().map(storageService::store).collect(Collectors.toSet());
    }

    Review review = modelMapper.map(request, Review.class);
    review.setAuthor(null);

    CulturalOffer culturalOffer = culturalOfferEntity.get();
    fileNames.forEach(fileName -> review.getImages().add(fileName));
    culturalOffer.addReview(review);

    repository.save(review);
    culturalOfferRepository.save(culturalOffer);

    return new ResponseEntity<>(modelMapper.map(review, AddReviewResponse.class), HttpStatus.CREATED);
  }
}
