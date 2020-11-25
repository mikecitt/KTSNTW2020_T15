package com.example.culturecontentapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class CulturalOffer extends Model {

  @NotBlank(message = "Name cannot be blank")
  @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
  @Column(nullable = false, unique = true)
  private String name;

  @NotBlank(message = "Description cannot be blank")
  @Size(min = 10, max = 256, message = "Description must be between 10 and 256 characters")
  @Column(nullable = false)
  private String description;

  @NotBlank(message = "Location cannot be blank")
  @Size(min = 5, max = 128, message = "Location must be between 5 and 128 characters")
  @Column(nullable = false)
  private String location;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "culturaloffer_id")
  private Set<Review> reviews;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "culturaloffer_id")
  private Set<Image> images;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "culturaloffer_id")
  private Set<News> news;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subtype_id")
  private SubType subType;

  public CulturalOffer() {
    reviews = new HashSet<>();
    images = new HashSet<>();
    news = new HashSet<>();
  }

  public CulturalOffer(String name, String description, String location) {
    this.name = name;
    this.description = description;
    this.location = location;
    reviews = new HashSet<>();
    images = new HashSet<>();
    news = new HashSet<>();
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Set<Review> getReviews() {
    return this.reviews;
  }

  public Set<Image> getImages() {
    return this.images;
  }

  public Set<News> getNews() {
    return this.news;
  }

  public SubType getSubType() {
    return this.subType;
  }

  public void setSubType(SubType subType) {
    this.subType = subType;
  }
}
