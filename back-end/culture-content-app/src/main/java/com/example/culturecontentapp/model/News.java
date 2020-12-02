package com.example.culturecontentapp.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class News extends Model {

  @NotBlank(message = "Text cannot be blank")
  @Size(min = 10, max = 256, message = "Text must be between 10 and 256 characters")
  @Column(nullable = false)
  private String text;

  @NotNull(message = "Date cannot be null")
  private LocalDateTime date;

  @ElementCollection
  @CollectionTable(name = "news_images")
  @Column(name = "image", nullable = false, length = 64)
  private Set<String> images;

  public News() {
    images = new HashSet<>();
  }

  public News(String text, LocalDateTime date) {
    this.text = text;
    this.date = date;
    images = new HashSet<>();
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

  public void setImages(Set<String> images){
    this.images = images;
  }
}
