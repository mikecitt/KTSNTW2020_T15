package com.example.culturecontentapp.api;
import com.example.culturecontentapp.payload.request.SubTypeRequest;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.service.SubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<List<SubTypeResponse>> getAllSubTypes(@PathVariable Long typeId){
    return new ResponseEntity<>(service.findAll(typeId), HttpStatus.OK);
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

  @DeleteMapping(value = "{typeId}/sub-types/{id}")
  public ResponseEntity<Void> deleteSubType(@PathVariable Long typeId, @PathVariable Long id){
    service.delete(typeId,id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(value = "{typeId}/sub-types/{id}")
  public ResponseEntity<SubTypeResponse> updateSubType(@RequestBody SubTypeRequest subTypeRequest,
                                                       @PathVariable Long typeId,
                                                       @PathVariable Long id){
    return new ResponseEntity<>(service.update(subTypeRequest, typeId, id), HttpStatus.OK);
  }

}
