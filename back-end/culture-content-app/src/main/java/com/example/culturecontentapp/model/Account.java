package com.example.culturecontentapp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Role")
@DiscriminatorValue("Administrator")
public class Account extends Model {

  @Email(message = "Email is not valid")
  @Size(max = 254, message = "Email cannot have more than 254 characters")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "Password cannot be blank")
  @Size(max = 60, message = "Password cannot have more than 60 characters")
  @Column(nullable = false)
  private String password;

  @NotBlank(message = "Username cannot be blank")
  @Size(min = 5, max = 64, message = "Username must have between 5 and 64 characters")
  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private boolean active;

  public Account() {

  }

  public Account(String email, String password, String username) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.active = false;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

}