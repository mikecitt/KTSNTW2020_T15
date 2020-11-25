package com.example.culturecontentapp.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class Image extends Model {

  @NotBlank(message = "Name cannot be blank")
  @Pattern(regexp = "/^.+\\.(jpg|jpeg|png)$/i", message = "Name must be valid")
  private String name;

  public Image() {

  }

  public Image(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
