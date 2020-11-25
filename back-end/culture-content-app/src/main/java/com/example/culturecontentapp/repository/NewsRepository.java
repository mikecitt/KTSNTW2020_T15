package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.News;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<Long, News> {

}
