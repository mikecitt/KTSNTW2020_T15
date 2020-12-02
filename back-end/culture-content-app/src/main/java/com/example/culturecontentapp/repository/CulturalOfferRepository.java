package com.example.culturecontentapp.repository;

import java.util.Optional;

import com.example.culturecontentapp.model.CulturalOffer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Long> {

  Optional<CulturalOffer> findByName(String name);
}
