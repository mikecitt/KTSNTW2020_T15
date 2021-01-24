package com.example.culturecontentapp.service;

import java.util.*;
import java.util.stream.Collectors;

import com.example.culturecontentapp.exception.CulturalOfferAlreadyExistsException;
import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.payload.request.CulturalOfferRequest;
import com.example.culturecontentapp.payload.response.CulturalOfferResponse;
import com.example.culturecontentapp.repository.CulturalOfferRepository;
import com.example.culturecontentapp.repository.SubTypeRepository;
import com.example.culturecontentapp.storage.StorageService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CulturalOfferService {

  private static String notFoundResponseMessage = "Cultural offer with the given id not found";

  private final CulturalOfferRepository repository;
  private final SubTypeRepository subTypeRepository;
  private final StorageService storageService;
  private final ModelMapper modelMapper;

  @Autowired
  public CulturalOfferService(CulturalOfferRepository repository, SubTypeRepository subTypeRepository,
      StorageService storageService, ModelMapper modelMapper) {
    this.repository = repository;
    this.subTypeRepository = subTypeRepository;
    this.storageService = storageService;
    this.modelMapper = modelMapper;
  }

  public ResponseEntity<CulturalOfferResponse> insert(Long subTypeId, CulturalOfferRequest request) {

    Optional<CulturalOffer> culturalOfferEntity = repository.findByName(request.getName());

    if (culturalOfferEntity.isPresent()) {
      throw new CulturalOfferAlreadyExistsException("Cultural offer with the given name already exists");
    }

    Optional<SubType> subTypeEntity = subTypeRepository.findById(subTypeId);

    if (!subTypeEntity.isPresent()) {
      throw new SubTypeNotFoundException("SubType with the given id not found");
    }

    Set<String> fileNames = new HashSet<>();
    for (String image : request.getImages()) {
      fileNames.add(storageService.store(image));
    }

    CulturalOffer culturalOffer = modelMapper.map(request, CulturalOffer.class);
    culturalOffer.setSubType(subTypeEntity.get());
    fileNames.forEach(fileName -> culturalOffer.getImages().add(fileName));
    repository.save(culturalOffer);

    return new ResponseEntity<>(modelMapper.map(culturalOffer, CulturalOfferResponse.class), HttpStatus.CREATED);
  }

  public ResponseEntity<CulturalOfferResponse> update(Long id, CulturalOfferRequest request) {

    Optional<CulturalOffer> culturalOfferEntity = repository.findById(id);

    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException(notFoundResponseMessage);
    }

    Optional<CulturalOffer> duplicateCulturalOffer = repository.findByNameAndIdNot(request.getName(), id);

    if (duplicateCulturalOffer.isPresent()) {
      throw new CulturalOfferAlreadyExistsException("Cultural offer with the given name already exists");
    }

    CulturalOffer culturalOffer = culturalOfferEntity.get();
    modelMapper.map(request, culturalOffer);

    Set<String> fileNames = new HashSet<>();
    for (String image : request.getImages()) {
      fileNames.add(storageService.store(image));
    }

    if (!fileNames.isEmpty()) {
      culturalOffer.getImages().forEach(storageService::delete);
      culturalOffer.getImages().clear();
      fileNames.forEach(fileName -> culturalOffer.getImages().add(fileName));
    }

    repository.save(culturalOffer);

    return new ResponseEntity<>(modelMapper.map(culturalOffer, CulturalOfferResponse.class), HttpStatus.OK);
  }

  public ResponseEntity<Void> delete(Long id) {

    Optional<CulturalOffer> culturalOfferEntity = repository.findById(id);

    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException(notFoundResponseMessage);
    }

    CulturalOffer culturalOffer = culturalOfferEntity.get();
    culturalOffer.getImages().forEach(storageService::delete);
    repository.delete(culturalOffer);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Page<CulturalOfferResponse>> select(Pageable pageable) {

    Page<CulturalOffer> culturalOffers = repository.findAll(pageable);

    return new ResponseEntity<>(
        culturalOffers.map(culturalOffer -> modelMapper.map(culturalOffer, CulturalOfferResponse.class)),
        HttpStatus.OK);
  }

  public ResponseEntity<List<CulturalOfferResponse>> selectAll() {
    List<CulturalOffer> culturalOffers = repository.findAll();
    return new ResponseEntity<>(culturalOffers.stream()
        .map(culturalOffer -> modelMapper.map(culturalOffer, CulturalOfferResponse.class)).collect(Collectors.toList()),
        HttpStatus.OK);
  }

  public ResponseEntity<CulturalOfferResponse> selectById(Long id) {

    Optional<CulturalOffer> culturalOfferEntity = repository.findById(id);

    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException(notFoundResponseMessage);
    }
    return new ResponseEntity<>(modelMapper.map(culturalOfferEntity.get(), CulturalOfferResponse.class), HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<List<CulturalOfferResponse>> searchAndFilter(String offerName, String subTypeName,
      String typeName) {
    List<CulturalOffer> foundOffers = repository.FindByFilterCriteria(offerName + "%", subTypeName + "%",
        typeName + "%");
    return new ResponseEntity<>(foundOffers.stream()
        .map(culturalOffer -> modelMapper.map(culturalOffer, CulturalOfferResponse.class)).collect(Collectors.toList()),
        HttpStatus.OK);
  }
}
