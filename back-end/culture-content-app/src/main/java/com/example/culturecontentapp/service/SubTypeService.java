package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.SubTypeAlreadyExistsException;
import com.example.culturecontentapp.exception.SubTypeNotFoundException;
import com.example.culturecontentapp.exception.TypeNotFoundException;
import com.example.culturecontentapp.model.SubType;
import com.example.culturecontentapp.model.Type;
import com.example.culturecontentapp.payload.request.SubTypeRequest;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.payload.response.TypeResponse;
import com.example.culturecontentapp.repository.SubTypeRepository;

import com.example.culturecontentapp.repository.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubTypeService {

  private final SubTypeRepository repository;
  private final ModelMapper mapper;
  private final TypeRepository typeRepository;

  @Autowired
  public SubTypeService(SubTypeRepository repository, ModelMapper mapper, TypeRepository typeRepository) {
    this.repository = repository;
    this.mapper = mapper;
    this.typeRepository = typeRepository;
  }

  public List<SubTypeResponse> findAll(Long typeId){
    List<SubType> subTypes = repository.findByTypeId(typeId);
    return toTypeDto(subTypes);
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

    SubType subType = mapper.map(typeRequest, SubType.class);
    subType.setType(type);

    return convertToDTO(repository.save(subType));
  }

  public void delete(Long typeId, Long id){
    SubType subType = repository.findByIdAndTypeId(id, typeId);
    if(subType == null)
      throw new SubTypeNotFoundException("Sub type with given id doesn't exist");
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
