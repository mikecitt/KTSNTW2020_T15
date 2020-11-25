package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.CulturalOffer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalOfferRepository extends JpaRepository<Long, CulturalOffer> {

}
