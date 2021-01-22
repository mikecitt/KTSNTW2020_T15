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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
      @RequestBody @Valid NewCulturalOfferRequest request) {
    return service.insert(subTypeId, request);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<EditCulturalOfferResponse> update(@PathVariable Long id,
      @RequestBody @Valid EditCulturalOfferRequest request) {
    return service.update(id, request);
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

  @GetMapping("/all")
  public ResponseEntity<List<SelectCulturalOfferResponse>> selectAll() {
    return service.selectAll();
  }

  @GetMapping("/search")
  public ResponseEntity<List<SelectCulturalOfferResponse>> searchAndFilter(@RequestParam String culturalOfferName,
      @RequestParam String subTypeName, @RequestParam String typeName) {
    return service.searchAndFilter(culturalOfferName, subTypeName, typeName);
  }
}
