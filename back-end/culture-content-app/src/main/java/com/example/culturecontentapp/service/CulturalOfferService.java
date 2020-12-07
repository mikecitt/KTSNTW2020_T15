package com.example.culturecontentapp.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.culturecontentapp.exception.CulturalOfferAlreadyExistsException;
import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.payload.request.NewCulturalOfferRequest;
import com.example.culturecontentapp.payload.response.EditCulturalOfferResponse;
import com.example.culturecontentapp.payload.response.NewCulturalOfferResponse;
import com.example.culturecontentapp.repository.CulturalOfferRepository;
import com.example.culturecontentapp.repository.SubTypeRepository;
import com.example.culturecontentapp.storage.StorageService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CulturalOfferService {

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

  public ResponseEntity<NewCulturalOfferResponse> insert(Long subTypeId, NewCulturalOfferRequest request,
      MultipartFile[] files) {

    Optional<CulturalOffer> culturalOfferentity = repository.findByName(request.getName());

    if (culturalOfferentity.isPresent()) {
      throw new CulturalOfferAlreadyExistsException("Cultural offer with the given name already exists");
    }

    Optional<SubType> subTypeEntity = subTypeRepository.findById(subTypeId);

    if (!subTypeEntity.isPresent()) {
      throw new SubTypeNotFoundException("SubType with the given id not found");
    }

    Set<String> fileNames = new HashSet<>();
    if (files.length >= 1 && !files[0].isEmpty()) {
      fileNames = Arrays.asList(files).stream().map(storageService::store).collect(Collectors.toSet());
    }

    CulturalOffer culturalOffer = modelMapper.map(request, CulturalOffer.class);
    culturalOffer.setSubType(subTypeEntity.get());
    fileNames.forEach(fileName -> culturalOffer.getImages().add(fileName));
    repository.save(culturalOffer);

    return new ResponseEntity<>(modelMapper.map(culturalOffer, NewCulturalOfferResponse.class), HttpStatus.CREATED);
  }

  public ResponseEntity<EditCulturalOfferResponse> update(Long id, NewCulturalOfferRequest request,
      MultipartFile[] files) {

    Optional<CulturalOffer> culturalOfferEntity = repository.findById(id);

    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException("Cultural offer with the given id not found");
    }

    CulturalOffer culturalOffer = culturalOfferEntity.get();
    modelMapper.map(request, culturalOffer);

    // TODO delte images from store
    Set<String> fileNames = new HashSet<>();
    if (files.length >= 1 && !files[0].isEmpty()) {
      culturalOffer.getImages().clear();
      fileNames = Arrays.asList(files).stream().map(storageService::store).collect(Collectors.toSet());
    }

    fileNames.forEach(fileName -> culturalOffer.getImages().add(fileName));
    repository.save(culturalOffer);

    return new ResponseEntity<>(modelMapper.map(culturalOffer, EditCulturalOfferResponse.class), HttpStatus.OK);
  }

  public ResponseEntity<Void> delete(Long id) {

    Optional<CulturalOffer> culturalOfferEntity = repository.findById(id);

    if (!culturalOfferEntity.isPresent()) {
      throw new CulturalOfferNotFoundException("Cultural offer with the given id not found");
    }

    repository.delete(culturalOfferEntity.get());

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
