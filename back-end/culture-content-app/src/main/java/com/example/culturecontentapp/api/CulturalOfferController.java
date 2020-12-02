package com.example.culturecontentapp.api;

import javax.validation.Valid;

import com.example.culturecontentapp.payload.request.NewCulturalOfferRequest;
import com.example.culturecontentapp.payload.response.NewCulturalOfferResponse;
import com.example.culturecontentapp.service.CulturalOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cultural-offer")
public class CulturalOfferController {

  private final CulturalOfferService service;

  @Autowired
  public CulturalOfferController(CulturalOfferService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<NewCulturalOfferResponse> insert(@RequestParam Long subTypeId,
      @RequestPart("request") @Valid NewCulturalOfferRequest request, @RequestPart("files") MultipartFile[] files) {
    return service.insert(subTypeId, request, files);
  }
}
