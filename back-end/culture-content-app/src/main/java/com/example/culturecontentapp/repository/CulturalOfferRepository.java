package com.example.culturecontentapp.repository;

import java.util.List;
import java.util.Optional;

import com.example.culturecontentapp.model.CulturalOffer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Long> {

  Page<CulturalOffer> findAll(Pageable pageable);

  Optional<CulturalOffer> findByName(String name);

  Optional<CulturalOffer> findByNameAndIdNot(String name, Long id);

  long countAllBySubTypeId(Long subTypeId);

  @Query("SELECT c FROM CulturalOffer c, SubType s WHERE "
      + "c.subType.id = s.id and UPPER(c.name) LIKE UPPER(:culturalOfferName) and "
      + "UPPER(s.name) LIKE UPPER(:subTypeName) and UPPER(s.type.name) LIKE UPPER(:typeName)")
  List<CulturalOffer> FindByFilterCriteria(String culturalOfferName, String subTypeName, String typeName);
}
