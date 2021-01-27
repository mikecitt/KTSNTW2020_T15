package com.example.culturecontentapp.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.culturecontentapp.exception.AccountNotFoundException;
import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.model.Review;
import com.example.culturecontentapp.model.User;
import com.example.culturecontentapp.payload.request.ReviewRequest;
import com.example.culturecontentapp.payload.response.ReviewResponse;
import com.example.culturecontentapp.repository.AccountRepository;
import com.example.culturecontentapp.repository.CulturalOfferRepository;
import com.example.culturecontentapp.repository.ReviewRepository;
import com.example.culturecontentapp.storage.StorageService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final ReviewRepository repository;
  private final CulturalOfferRepository culturalOfferRepository;
  private final ModelMapper modelMapper;
  private final StorageService storageService;
  private final AccountRepository accountRepository;

  @Autowired
  public ReviewService(ReviewRepository repository, CulturalOfferRepository culturalOfferRepository,
      StorageService storageService, ModelMapper modelMapper, AccountRepository accountRepository) {
    this.repository = repository;
    this.culturalOfferRepository = culturalOfferRepository;
    this.storageService = storageService;
    this.modelMapper = modelMapper;
    this.accountRepository = accountRepository;
  }

  public ResponseEntity<Page<ReviewResponse>> get(Long culturalOfferId, Pageable pageable) {
    Optional<CulturalOffer> culturalOfferEntity = this.culturalOfferRepository.findById(culturalOfferId);
    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException("Cultural offer with the given id not exists");
    }

    Page<Review> reviews = repository.findByCulturalOffer(culturalOfferId, pageable);

    return new ResponseEntity<>(reviews.map(review -> modelMapper.map(review, ReviewResponse.class)), HttpStatus.OK);
  }

  public ResponseEntity<ReviewResponse> addReview(Long culturalOfferId, ReviewRequest request) {

    org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
    Optional<Account> entity = accountRepository.findByEmail(loggedUser.getUsername());
    if (!entity.isPresent()) {
      throw new AccountNotFoundException("Provided account is not found in the database");
    }
    User user = (User) entity.get();

    Optional<CulturalOffer> culturalOfferEntity = this.culturalOfferRepository.findById(culturalOfferId);
    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException("Cultural offer with the given id not exists");
    }

    Set<String> fileNames = new HashSet<>();
    for (String image : request.getImages()) {
      fileNames.add(storageService.store(image));
    }

    Review review = modelMapper.map(request, Review.class);

    review.setAuthor(user);

    CulturalOffer culturalOffer = culturalOfferEntity.get();
    review.setImages(fileNames);
    culturalOffer.addReview(review);

    repository.save(review);
    culturalOfferRepository.save(culturalOffer);

    return new ResponseEntity<>(modelMapper.map(review, ReviewResponse.class), HttpStatus.CREATED);
  }
}
