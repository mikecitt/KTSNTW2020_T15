package com.example.culturecontentapp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditCulturalOfferRequest {

  @NotBlank(message = "Name cannot be blank")
  @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
  @JsonProperty("name")
  private String name;

  @NotBlank(message = "Description cannot be blank")
  @Size(min = 10, max = 256, message = "Description must be between 10 and 256 characters")
  @JsonProperty("description")
  private String description;

  @NotBlank(message = "Location cannot be blank")
  @Size(min = 5, max = 128, message = "Location must be between 5 and 128 characters")
  @JsonProperty("location")
  private String location;

  protected EditCulturalOfferRequest() {

  }

  public EditCulturalOfferRequest(String name, String description, String location) {
    this.name = name;
    this.description = description;
    this.location = location;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public String getLocation() {
    return this.location;
  }
}
