package com.example.culturecontentapp.api;

import javax.validation.Valid;

import com.example.culturecontentapp.payload.request.EditCulturalOfferRequest;
import com.example.culturecontentapp.payload.request.NewCulturalOfferRequest;
import com.example.culturecontentapp.payload.response.EditCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.NewCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.SelectCulturalOfferResponse;
import com.example.culturecontentapp.service.CulturalOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/cultural-offer")
public class CulturalOfferController {

  private final CulturalOfferService service;

  @Autowired
  public CulturalOfferController(CulturalOfferService service) {
    this.service = service;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<NewCulturalOfferResponse> insert(@RequestParam Long subTypeId,
      @RequestPart("request") @Valid NewCulturalOfferRequest request, @RequestPart("files") MultipartFile[] files) {
    return service.insert(subTypeId, request, files);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<EditCulturalOfferResponse> update(@PathVariable Long id,
      @RequestPart("request") @Valid EditCulturalOfferRequest request, @RequestPart("files") MultipartFile[] files) {
    return service.update(id, request, files);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return service.delete(id);
  }

  @GetMapping
  public ResponseEntity<Page<SelectCulturalOfferResponse>> select(Pageable pageable) {
    return service.select(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SelectCulturalOfferResponse> selectById(@PathVariable Long id) {
    return service.selectById(id);
  }

  @GetMapping("/search")
  public ResponseEntity<List<SelectCulturalOfferResponse>> searchAndFilter(@RequestParam String culturalOfferName,
      @RequestParam String subTypeName, @RequestParam String typeName) {
    return service.searchAndFilter(culturalOfferName, subTypeName, typeName);
  }
}
