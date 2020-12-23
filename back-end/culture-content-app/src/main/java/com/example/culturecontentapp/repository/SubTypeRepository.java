package com.example.culturecontentapp.repository;

import com.example.culturecontentapp.model.SubType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface SubTypeRepository extends JpaRepository<SubType, Long> {

    Long countAllByTypeId(Long id);

    List<SubType> findAllByTypeId(Long id, Pageable pageable);

    SubType findByIdAndTypeId(Long id, Long typeId);

    SubType findByName(String name);

    SubType findByNameAndIdNot(String name, Long id);


//    @Query("SELECT s.name FROM SubType s WHERE s.id=:id and s.type.id=:typeId")
//    String findNameByIdAndTypeId(Long id, Long typeId);
}
