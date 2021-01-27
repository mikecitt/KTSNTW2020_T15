package com.example.culturecontentapp.payload.request;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewsRequest {

  private Long id;

  @NotBlank(message = "Description must be between 10 and 256 characters")
  @Size(min = 10, max = 256, message = "Description must be between 10 and 256 characters")
  private String text;

  @NotNull(message = "Date cannot be null")
  private LocalDateTime date;

  private Set<String> images;

  public NewsRequest(){
    images = new HashSet<>();
  }  

  public NewsRequest(String text, LocalDateTime date){
    this.text = text;
    this.date = date;
    this.images = new HashSet<>();
  }

  public NewsRequest(Long id, String text, LocalDateTime date){
    this.id = id;
    this.text = text;
    this.date = date;
    this.images = new HashSet<>();
  }

  public Long getId(){
    return this.id;
  }

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
