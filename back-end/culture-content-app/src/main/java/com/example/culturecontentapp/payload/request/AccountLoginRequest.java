package com.example.culturecontentapp.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountLoginRequest {

  @Email(message = "Email must be valid")
  @Size(max = 50, message = "Email cannot have more than 50 characters")
  @JsonProperty("email")
  protected String email;

  @NotBlank(message = "Password cannot be blank")
  @Size(max = 60, message = "Password cannot have more than 60 characters")
  @JsonProperty("password")
  protected String password;


  public AccountLoginRequest(String email, String pass){
    this.email = email;
    this.password = pass;
  }

  protected AccountLoginRequest() {

  public AccountLoginRequest() {


  }

  public AccountLoginRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
