package com.example.culturecontentapp.service;

import java.util.*;
import java.util.stream.Collectors;

import com.example.culturecontentapp.exception.CulturalOfferAlreadyExistsException;
import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.payload.request.EditCulturalOfferRequest;
import com.example.culturecontentapp.payload.request.NewCulturalOfferRequest;
import com.example.culturecontentapp.payload.response.EditCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.NewCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.SelectCulturalOfferResponse;
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
import org.springframework.web.multipart.MultipartFile;

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

  public ResponseEntity<NewCulturalOfferResponse> insert(Long subTypeId, NewCulturalOfferRequest request) {

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

    return new ResponseEntity<>(modelMapper.map(culturalOffer, NewCulturalOfferResponse.class), HttpStatus.CREATED);
  }

  public ResponseEntity<EditCulturalOfferResponse> update(Long id, EditCulturalOfferRequest request,
      MultipartFile[] files) {

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
    for (MultipartFile file : files) {
      if (!file.isEmpty()) {
        fileNames.add(storageService.store(file));
      }
    }

    if (!fileNames.isEmpty()) {
      culturalOffer.getImages().forEach(storageService::delete);
      culturalOffer.getImages().clear();
      fileNames.forEach(fileName -> culturalOffer.getImages().add(fileName));
    }

    repository.save(culturalOffer);

    return new ResponseEntity<>(modelMapper.map(culturalOffer, EditCulturalOfferResponse.class), HttpStatus.OK);
  }

  public ResponseEntity<Void> delete(Long id) {

    Optional<CulturalOffer> culturalOfferEntity = repository.findById(id);

    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException(notFoundResponseMessage);
    }

    repository.delete(culturalOfferEntity.get());

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Page<SelectCulturalOfferResponse>> select(Pageable pageable) {

    Page<CulturalOffer> culturalOffers = repository.findAll(pageable);

    return new ResponseEntity<>(
        culturalOffers.map(culturalOffer -> modelMapper.map(culturalOffer, SelectCulturalOfferResponse.class)),
        HttpStatus.OK);
  }

  public ResponseEntity<List<SelectCulturalOfferResponse>> selectAll() {
    List<CulturalOffer> culturalOffers = repository.findAll();
    return new ResponseEntity<>(
        culturalOffers.stream().map(culturalOffer -> modelMapper.map(culturalOffer, SelectCulturalOfferResponse.class))
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }

  public ResponseEntity<SelectCulturalOfferResponse> selectById(Long id) {

    Optional<CulturalOffer> culturalOfferEntity = repository.findById(id);

    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException(notFoundResponseMessage);
    }
    return new ResponseEntity<>(modelMapper.map(culturalOfferEntity.get(), SelectCulturalOfferResponse.class),
        HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<List<SelectCulturalOfferResponse>> searchAndFilter(String offerName, String subTypeName,
      String typeName) {
    List<CulturalOffer> foundOffers = repository.FindByFilterCriteria(offerName + "%", subTypeName + "%",
        typeName + "%");
    return new ResponseEntity<>(
        foundOffers.stream().map(culturalOffer -> modelMapper.map(culturalOffer, SelectCulturalOfferResponse.class))
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }
}
