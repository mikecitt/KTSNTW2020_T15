package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.News;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News as n WHERE culturaloffer_id=:id")
    Page<News> findByCulturalOffer(Long id, Pageable pabeable);
}
