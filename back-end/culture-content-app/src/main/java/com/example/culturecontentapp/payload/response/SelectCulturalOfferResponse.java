package com.example.culturecontentapp.payload.response;

import java.util.Set;

import com.example.culturecontentapp.model.Location;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SelectCulturalOfferResponse {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("location")
  private Location location;

  @JsonProperty("images")
  private Set<String> images;

  @JsonProperty("subType")
  private SubTypeResponse subType;

  protected SelectCulturalOfferResponse() {

  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public Location getLocation() {
    return this.location;
  }

  public Set<String> getImages() {
    return this.images;
  }

  public SubTypeResponse getSubType() {
    return this.subType;
  }
}
