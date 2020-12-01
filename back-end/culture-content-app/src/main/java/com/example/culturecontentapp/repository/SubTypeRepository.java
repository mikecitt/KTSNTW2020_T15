package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.SubType;

import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubTypeRepository extends JpaRepository<SubType, Long> {

    List<SubType> findByTypeId(Long id);

    SubType findByIdAndTypeId(Long id, Long typeId);

    SubType findByName(String name);
}
