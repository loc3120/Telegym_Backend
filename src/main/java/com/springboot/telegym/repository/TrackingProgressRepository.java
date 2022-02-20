package com.springboot.telegym.repository;

import com.springboot.telegym.entity.TrackingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingProgressRepository extends JpaRepository<TrackingProgress, String> {
}
