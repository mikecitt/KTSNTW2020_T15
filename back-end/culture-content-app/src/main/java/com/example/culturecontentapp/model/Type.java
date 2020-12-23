package com.example.culturecontentapp.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Iterator;
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

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "type", cascade = CascadeType.ALL)
  private Set<SubType> subTypes;

  public void update(String newName){
    this.name = name;
  }

  public void removeSubType(SubType subType){
    this.subTypes.remove(subType);
    subType.setType(null);
  }
  public void removeAllSubTypes(){
    Iterator ite = subTypes.iterator();
    SubType s;
    while (ite.hasNext()) {
      s =(SubType) ite.next();
      s.setType(null);
      ite.remove();
    }
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
