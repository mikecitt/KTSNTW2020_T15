package com.example.culturecontentapp.api;

import com.example.culturecontentapp.service.SubTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubTypeController {

  private final SubTypeService service;

  @Autowired
  public SubTypeController(SubTypeService service) {
    this.service = service;
  }
}
