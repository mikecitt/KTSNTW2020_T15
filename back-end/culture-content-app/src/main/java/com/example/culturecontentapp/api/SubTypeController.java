package com.example.culturecontentapp.api;

import com.example.culturecontentapp.payload.request.SubTypeRequest;
import com.example.culturecontentapp.payload.response.SubTypeResponse;
import com.example.culturecontentapp.service.SubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SubTypeController {

  private final SubTypeService service;

  @Autowired
  public SubTypeController(SubTypeService service) {
    this.service = service;
  }

  @GetMapping(value = "/sub-types")
  public ResponseEntity<Page<SubTypeResponse>> getAllSubTypes(@RequestParam Long typeId, Pageable pageable) {
    return new ResponseEntity<>(service.findAll(typeId, pageable), HttpStatus.OK);
  }

  @GetMapping(value = "/sub-types/all")
  public ResponseEntity<List<SubTypeResponse>> getAllSubTypes(@RequestParam Long typeId){
    return new ResponseEntity<>(service.findAll(typeId), HttpStatus.OK);
  }

  @GetMapping(value = "/sub-types/{id}")
  public ResponseEntity<SubTypeResponse> getSybType(@RequestParam Long typeId, @PathVariable Long id) {
    return new ResponseEntity<>(service.findOne(typeId, id), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(value = "/sub-types")
  public ResponseEntity<SubTypeResponse> createSubType(@RequestBody @Valid SubTypeRequest typeRequest,
      @RequestParam Long typeId) {
    return new ResponseEntity<>(service.create(typeRequest, typeId), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/sub-types/{id}")
  public ResponseEntity<Void> deleteSubType(@RequestParam Long typeId, @PathVariable Long id) {
    service.delete(typeId, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/sub-types/{id}")
  public ResponseEntity<SubTypeResponse> updateSubType(@RequestBody @Valid SubTypeRequest subTypeRequest,
      @RequestParam Long typeId, @PathVariable Long id) {
    return new ResponseEntity<>(service.update(subTypeRequest, typeId, id), HttpStatus.OK);
  }

}
