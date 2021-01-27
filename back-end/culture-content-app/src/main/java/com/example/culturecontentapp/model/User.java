package com.example.culturecontentapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ForeignKey;
import javax.persistence.ConstraintMode;

@Entity
@DiscriminatorValue("User")
public class User extends Account {

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.PERSIST }, targetEntity = CulturalOffer.class)
  @JoinTable(name = "user_culturaloffers", joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "culturaloffer_id", nullable = false, updatable = false), foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
  private Set<CulturalOffer> subscriptions;

  public User() {
    this.subscriptions = new HashSet<>();
  }

  public User(String email, String password, String username) {
    super(email, password, username);
    this.subscriptions = new HashSet<>();
  }

  public Set<CulturalOffer> getSubscriptions() {
    return this.subscriptions;
  }

  public boolean isSubscribedTo(Long culturalOfferId) {
    return subscriptions.stream().anyMatch(offer -> offer.getId().equals(culturalOfferId));
  }
}
