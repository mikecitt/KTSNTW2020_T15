package com.example.culturecontentapp.service;

import com.example.culturecontentapp.repository.CulturalOfferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CulturalOfferService {

  private final CulturalOfferRepository repository;

  @Autowired
  public CulturalOfferService(CulturalOfferRepository repository) {
    this.repository = repository;
  }
}
