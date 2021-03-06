package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.SubTypeAlreadyExistsException;
import com.example.culturecontentapp.exception.SubTypeHasCulturalOffersException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.exception.TypeNotFoundException;
import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.model.Type;
import com.example.culturecontentapp.payload.request.SubTypeRequest;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.repository.CulturalOfferRepository;
import com.example.culturecontentapp.repository.SubTypeRepository;

import com.example.culturecontentapp.repository.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubTypeService {

  private final SubTypeRepository repository;
  private final ModelMapper mapper;
  private final TypeRepository typeRepository;
  private final CulturalOfferRepository culturalOfferRepository;

  @Autowired
  public SubTypeService(SubTypeRepository repository, ModelMapper mapper,
                        TypeRepository typeRepository, CulturalOfferRepository culturalOfferRepository) {
    this.repository = repository;
    this.mapper = mapper;
    this.typeRepository = typeRepository;
    this.culturalOfferRepository = culturalOfferRepository;
  }

  public Page<SubTypeResponse> findAll(Long typeId, Pageable pageable){
    Page<SubType> subTypes = repository.findAllByTypeId(typeId,pageable);
//    List<SubType> subTypes = repository.findByTypeId(typeId);
    return new PageImpl<>(subTypes.map(sub -> mapper.map(sub, SubTypeResponse.class)).toList(), subTypes.getPageable(), subTypes.getTotalElements());
  }

  public List<SubTypeResponse> findAll(Long typeId){
    return toTypeDto(repository.findAllByTypeId(typeId));
  }

  public SubTypeResponse findOne(Long typeId, Long id){
    SubType subType = repository.findByIdAndTypeId(id, typeId);
    if(subType == null)
      throw new SubTypeNotFoundException("Subtype with given id doesn't exist");
    return convertToDTO(subType);
  }

  public SubTypeResponse create(SubTypeRequest typeRequest, Long typeId){
    if(repository.findByName(typeRequest.getName()) != null)
      throw new SubTypeAlreadyExistsException("Subtype with given name already exists");
    Type type = typeRepository.findById(typeId).orElse(null);
    if(type == null)
      throw new TypeNotFoundException("Chosen type doesn't exist");

    SubType subType = mapper.map(typeRequest, SubType.class);   //ovde bi trealo da pukne jer mu saljemo id
    subType.setType(type);

    return convertToDTO(repository.save(subType));
  }
  public void delete(Long typeId, Long id){
    SubType subType = repository.findByIdAndTypeId(id, typeId);

    if(subType == null)
      throw new SubTypeNotFoundException("Sub type with given id doesn't exist");
    if(culturalOfferRepository.countAllBySubTypeId(id) != 0)
      throw new SubTypeHasCulturalOffersException("Sub type with given id has referenced cultural offers");

    if(subType.getType() != null) //mozemo zabraniti i kad ima tip
      subType.getType().removeSubType(subType);

    repository.delete(subType);
  }

  public SubTypeResponse update(SubTypeRequest subTypeRequest, Long typeId, Long id){
    SubType existingSubType = repository.findByIdAndTypeId(id, typeId);
    if(existingSubType == null)
      throw new SubTypeNotFoundException("Sub type with given id doesn't exist");
    if(repository.findByNameAndIdNot(subTypeRequest.getName(),id) != null)
      throw new SubTypeAlreadyExistsException("Subtype with given name already exists");

    existingSubType.setName(subTypeRequest.getName());
    return convertToDTO(repository.save(existingSubType));
  }

  private List<SubTypeResponse> toTypeDto(List<SubType> types){
    return types.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
  }

  private SubTypeResponse convertToDTO(SubType subType){
    return mapper.map(subType, SubTypeResponse.class);
  }

}
