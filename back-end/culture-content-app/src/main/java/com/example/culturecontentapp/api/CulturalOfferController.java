package com.example.culturecontentapp.api;

import javax.validation.Valid;

import com.example.culturecontentapp.payload.request.NewCulturalOfferRequest;
import com.example.culturecontentapp.payload.response.EditCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.NewCulturalOfferResponse;
import com.example.culturecontentapp.service.CulturalOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @PutMapping("/{id}")
  public ResponseEntity<EditCulturalOfferResponse> update(@PathVariable Long id,
      @RequestPart("request") @Valid NewCulturalOfferRequest request, @RequestPart("files") MultipartFile[] files) {
    return service.update(id, request, files);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return service.delete(id);
  }
}
