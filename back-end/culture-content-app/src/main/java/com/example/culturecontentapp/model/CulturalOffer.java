package com.example.culturecontentapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
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

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "location_id")
  private Location location;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "culturaloffer_id")
  private Set<Review> reviews;

  @ElementCollection
  @CollectionTable(name = "culturaloffer_images")
  @Column(name = "image", nullable = false, length = 64)
  private Set<String> images;

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

  public CulturalOffer(String name, String description, Location location) {
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

  public Location getLocation() {
    return this.location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Set<Review> getReviews() {
    return this.reviews;
  }

  public Set<String> getImages() {
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

  public void addNews(News news) {
    this.news.add(news);
  }

  public void addReview(Review review) {
    this.reviews.add(review);
  }
}
