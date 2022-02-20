package com.springboot.telegym.repository;

import com.springboot.telegym.entity.AccessManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessManagementRepository extends JpaRepository<AccessManagement, String> {
}
