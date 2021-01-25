package com.example.culturecontentapp.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class SubType extends Model {

  @NotBlank(message = "Name cannot be blank")
  @Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters")
  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "type_id")
  private Type type;

  public SubType() {
  }

  public SubType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Type getType() {
    return this.type;
  }

  public void setType(Type type) {
    this.type = type;
  }
}
