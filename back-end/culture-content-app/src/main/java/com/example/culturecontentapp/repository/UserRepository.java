package com.example.culturecontentapp.repository;

import java.util.List;

import com.example.culturecontentapp.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User as u left join u.subscriptions c WHERE c.id =:id")
    List<User> findByCulturalOffersId(Long id);

}
