package com.example.culturecontentapp.repository;

import java.util.List;

import com.example.culturecontentapp.model.News;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News as n WHERE culturaloffer_id=:id")
    List<News> findByCulturalOffer(Long id);
}
