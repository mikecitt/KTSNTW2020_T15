package com.example.culturecontentapp.repository;

import java.util.List;

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

    @Query("SELECT n FROM News as n WHERE culturaloffer_id IN :ids")
    Page<News> findByCulturalOffers(List<Long> ids, Pageable pabeable);

    // @Query("select n.date, n.text, c.name from news n INNER JOIN cultural_offer c
    // ON n.culturaloffer_id = c.id INNER JOIN User_culturaloffers u ON c.id =
    // u.culturaloffer_id WHERE u.user_id = :id ORDER BY n.date DESC")
    // Page<News> findByUser(Long id, Pageable pabeable);
}
