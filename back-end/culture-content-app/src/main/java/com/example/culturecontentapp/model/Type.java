package com.example.culturecontentapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Type extends Model {

  @NotBlank(message = "Name cannot be blank")
  @Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters")
  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
  private Set<SubType> subTypes;

  public void update(String newName){
    this.name = name;
  }

  public Type() {
    this.subTypes = new HashSet<>();
  }

  public Type(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<SubType> getSubTypes() {
    return this.subTypes;
  }
}
