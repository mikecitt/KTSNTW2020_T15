package com.example.culturecontentapp.api;

import com.example.culturecontentapp.payload.request.TypeRequest;
import com.example.culturecontentapp.payload.response.TypeResponse;
import com.example.culturecontentapp.service.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/types")
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

  @GetMapping(value = "/{id}")
  public ResponseEntity<TypeResponse> getCulturalOfferType(@PathVariable Long id){
    return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<TypeResponse> createCulturalOfferType(@RequestBody TypeRequest typeRequestDto) {
    return new ResponseEntity<>(service.create(typeRequestDto), HttpStatus.CREATED);
  }
}
