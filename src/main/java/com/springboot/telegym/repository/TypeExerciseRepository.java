package com.springboot.telegym.repository;

import com.springboot.telegym.entity.TypeExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeExerciseRepository extends JpaRepository<TypeExercise, String> {

    Optional<TypeExercise> findByTypename(String typeName);
}
