package com.example.culturecontentapp.service;

import com.example.culturecontentapp.repository.TypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService {

  private final TypeRepository repository;

  @Autowired
  public TypeService(TypeRepository repository) {
    this.repository = repository;
  }
}
