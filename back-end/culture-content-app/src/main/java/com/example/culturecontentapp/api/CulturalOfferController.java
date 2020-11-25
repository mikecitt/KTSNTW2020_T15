package com.example.culturecontentapp.api;

import com.example.culturecontentapp.service.CulturalOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CulturalOfferController {

  private final CulturalOfferService service;

  @Autowired
  public CulturalOfferController(CulturalOfferService service) {
    this.service = service;
  }
}
