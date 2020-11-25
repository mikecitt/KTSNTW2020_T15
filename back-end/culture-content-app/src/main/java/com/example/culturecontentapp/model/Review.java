package com.example.culturecontentapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Review extends Model {

  @Min(0)
  @Max(5)
  @Column(nullable = false)
  private Short rating;

  @NotBlank(message = "Comment cannot be blank")
  @Size(min = 5, max = 128, message = "Comment must be between 5 and 128 characters")
  @Column(nullable = false)
  private String comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User author;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "review_id")
  private Set<Image> images;

  public Review() {
    images = new HashSet<>();
  }

  public Review(Short rating, String comment) {
    this.rating = rating;
    this.comment = comment;
    images = new HashSet<>();
  }

  public Short getRating() {
    return this.rating;
  }

  public void setRating(Short rating) {
    this.rating = rating;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public User getAuthor() {
    return this.author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Set<Image> getImages() {
    return this.images;
  }
}
