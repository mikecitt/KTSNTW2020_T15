package com.example.culturecontentapp.service;

import com.example.culturecontentapp.repository.SubTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubTypeService {

  private final SubTypeRepository repository;

  @Autowired
  public SubTypeService(SubTypeRepository repository) {
    this.repository = repository;
  }
}
