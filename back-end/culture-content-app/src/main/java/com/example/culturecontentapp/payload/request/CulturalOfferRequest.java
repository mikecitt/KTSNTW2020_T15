package com.example.culturecontentapp.payload.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CulturalOfferRequest {

  @NotBlank(message = "Name cannot be blank")
  @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
  @JsonProperty("name")
  private String name;

  @NotBlank(message = "Description cannot be blank")
  @Size(min = 10, max = 256, message = "Description must be between 10 and 256 characters")
  @JsonProperty("description")
  private String description;

  @Valid
  @NotNull(message = "Location is required")
  @JsonProperty("location")
  private CulturalOfferLocationRequest location;

  @NotNull(message = "Images are required")
  @JsonProperty("images")
  private String[] images;

  protected CulturalOfferRequest() {

  }

  public CulturalOfferRequest(String name, String description, CulturalOfferLocationRequest location, String[] images) {
    this.name = name;
    this.description = description;
    this.location = location;
    this.images = images;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public CulturalOfferLocationRequest getLocation() {
    return this.location;
  }

  public String[] getImages() {
    return this.images;
  }
}
