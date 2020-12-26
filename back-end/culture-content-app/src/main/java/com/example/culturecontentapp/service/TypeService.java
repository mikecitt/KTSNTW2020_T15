package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.TypeAlreadyExistsException;
import com.example.culturecontentapp.exception.TypeHasSubTypesException;
import com.example.culturecontentapp.exception.TypeNotFoundException;
import com.example.culturecontentapp.model.Type;
import com.example.culturecontentapp.payload.request.TypeRequest;
import com.example.culturecontentapp.payload.response.TypeResponse;
import com.example.culturecontentapp.repository.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TypeService {

  private final TypeRepository repository;
  private final ModelMapper mapper;

  @Autowired
  public TypeService(TypeRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<TypeResponse> findAll(){
    List<Type> types = repository.findAll();
    return types.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
  }

  public Page<TypeResponse> findAll(Pageable pageable){
    Page<Type> types = repository.findAll(pageable);
    return new PageImpl<>(toTypeDto(types),types.getPageable(),types.getTotalElements());
//    return toTypeDto(types);
  }

  public TypeResponse findById(Long id){
    Optional<Type> type = repository.findById(id);
    if(type.isEmpty())
      throw new TypeNotFoundException("Cultural offer type with given id doesn't exist");
    return convertToDTO(type.get());
  }

  public TypeResponse create(TypeRequest typeRequest){
    if(repository.findByName(typeRequest.getName()) != null)
      throw new TypeAlreadyExistsException("Cultural offer type with given name already exists");
    Type type = mapper.map(typeRequest, Type.class);

    return convertToDTO(repository.save(type));
  }

  public TypeResponse update(TypeRequest newEntity, Long id){
    Type existingType = repository.findById(id).orElse(null);
    if(existingType == null)
      throw new TypeNotFoundException("Cultural offer type with given id doesn't exist");

    if(repository.findByNameAndIdNot(newEntity.getName(), id) != null)
      throw new TypeAlreadyExistsException("Cultural offer type with given name already exists");
    existingType.setName(newEntity.getName());

    return convertToDTO(repository.save(existingType));
  }
  @Transactional
  public void delete(Long id){
    //ne brisemo subType
    Optional<Type> type = repository.findById(id);
    if(type.isEmpty())
      throw new TypeNotFoundException("Cultural offer type with given id doesn't exist");
    if(type.get().getSubTypes().size() != 0)
      throw new TypeHasSubTypesException("Can't delete type");
    //type.get().removeAllSubTypes();;
    repository.delete(type.get());
  }

  private List<TypeResponse> toTypeDto(Page<Type> types){
    return types.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
  }

  private TypeResponse convertToDTO(Type type){
    return mapper.map(type, TypeResponse.class);
  }
}
