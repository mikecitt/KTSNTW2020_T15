package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review as r WHERE culturaloffer_id = :id")
    Page<Review> findByCulturalOffer(@Param("id") Long id, Pageable pageable);
}
