package com.example.culturecontentapp.api;

import com.example.culturecontentapp.payload.request.TypeRequest;
import com.example.culturecontentapp.payload.response.TypeResponse;
import com.example.culturecontentapp.service.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeController {

  private final TypeService service;

  @Autowired
  public TypeController(TypeService service) {
    this.service = service;
  }

  @GetMapping("")
  public ResponseEntity<Page<TypeResponse>> getAllCulturalOfferTypes(Pageable pageable) {
    return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<TypeResponse>> getAllCulturalOfferTypes(){
    return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<TypeResponse> getCulturalOfferType(@PathVariable Long id) {
    return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("")
  public ResponseEntity<TypeResponse> createCulturalOfferType(@RequestBody @Valid TypeRequest typeRequestDto) {
    return new ResponseEntity<>(service.create(typeRequestDto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<TypeResponse> updateCulturalOfferType(@RequestBody @Valid TypeRequest typeRequest,
      @PathVariable Long id) {
    return new ResponseEntity<>(service.update(typeRequest, id), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteCulturalOfferType(@PathVariable Long id) {
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
