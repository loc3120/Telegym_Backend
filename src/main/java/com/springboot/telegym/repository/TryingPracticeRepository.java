package com.springboot.telegym.repository;

import com.springboot.telegym.entity.TryingPractice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TryingPracticeRepository extends JpaRepository<TryingPractice, String> {
}
