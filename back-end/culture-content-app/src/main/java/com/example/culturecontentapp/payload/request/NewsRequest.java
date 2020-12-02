package com.example.culturecontentapp.payload.request;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewsRequest {

    @NotBlank(message = "Text cannot be blank")
  @Size(min = 10, max = 256, message = "Description must be between 10 and 256 characters")
  private String text;

  @NotNull(message = "Date cannot be null")
  private LocalDateTime date;

  private Set<String> images;

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getDate() {
    return this.date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Set<String> getImages() {
    return this.images;
  }
}
