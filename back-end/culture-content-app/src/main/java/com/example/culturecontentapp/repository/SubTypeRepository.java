package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.SubType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTypeRepository extends JpaRepository<SubType, Long> {

}
