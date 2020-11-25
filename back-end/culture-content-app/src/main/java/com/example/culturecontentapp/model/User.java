package com.example.culturecontentapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("User")
public class User extends Account {

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_culturaloffers", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "culturaloffer_id"))
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
}
