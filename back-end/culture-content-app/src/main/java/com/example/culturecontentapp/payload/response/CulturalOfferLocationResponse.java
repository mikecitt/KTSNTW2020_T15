package com.example.culturecontentapp.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CulturalOfferLocationResponse {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("address")
  private String address;

  @JsonProperty("latitude")
  private Double latitude;

  @JsonProperty("longitude")
  private Double longitude;

  protected CulturalOfferLocationResponse() {

  }

  public Long getId() {
    return this.id;
  }

  public String getAddress() {
    return address;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }
}
