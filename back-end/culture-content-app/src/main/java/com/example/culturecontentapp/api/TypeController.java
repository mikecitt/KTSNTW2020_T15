package com.example.culturecontentapp.api;

import com.example.culturecontentapp.service.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeController {

  private final TypeService service;

  @Autowired
  public TypeController(TypeService service) {
    this.service = service;
  }
}
