package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.Type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);
    Type findByNameAndIdNot(String name, Long id);
}
