package com.example.culturecontentapp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CulturalOfferLocationRequest {

  @JsonProperty("address")
  @NotBlank(message = "Name cannot be blank")
  private String address;

  @JsonProperty("latitude")
  @NotNull(message = "Latitude is required")
  private Double latitude;

  @JsonProperty("longitude")
  @NotNull(message = "Longitude is required")
  private Double longitude;

  protected CulturalOfferLocationRequest() {

  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }
}
