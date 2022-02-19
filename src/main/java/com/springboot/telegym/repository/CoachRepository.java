package com.springboot.telegym.repository;

import com.springboot.telegym.entity.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, String> {

    Optional<Coach> findByEmail(String email);

    @Query("select c from Coach c where lower(c.typeExercise.typename) in :typeCoach")
    Page<Coach> printAllCoach(Pageable pageable, @Param("typeCoach") List<String> typeCoach);
}
