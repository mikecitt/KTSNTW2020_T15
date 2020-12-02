package com.example.culturecontentapp.api;
import com.example.culturecontentapp.payload.request.SubTypeRequest;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.service.SubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SubTypeController {

  private final SubTypeService service;

  @Autowired
  public SubTypeController(SubTypeService service) {
    this.service = service;
  }

  @GetMapping(value = "{typeId}/sub-types")
  public ResponseEntity<Page<SubTypeResponse>> getAllSubTypes(@PathVariable Long typeId, Pageable pageable){
    return new ResponseEntity<>(service.findAll(typeId, pageable), HttpStatus.OK);
  }

  @GetMapping(value = "{typeId}/sub-types/{id}")
  public ResponseEntity<SubTypeResponse> getSybType(@PathVariable Long typeId, @PathVariable Long id){
    return new ResponseEntity<>(service.findOne(typeId, id), HttpStatus.OK);
  }

  @PostMapping(value = "{typeId}/sub-types")
  public ResponseEntity<SubTypeResponse> createSubType(@RequestBody SubTypeRequest typeRequest,
                                                       @PathVariable Long typeId){
    return new ResponseEntity<>(service.create(typeRequest, typeId), HttpStatus.CREATED);
  }

}
