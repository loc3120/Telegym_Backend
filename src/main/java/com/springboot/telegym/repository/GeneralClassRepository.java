package com.springboot.telegym.repository;

import com.springboot.telegym.entity.GeneralClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralClassRepository extends JpaRepository<GeneralClass, String> {

    @Procedure(procedureName = "Select_GeneralClass")
    List<GeneralClass> selectGC();
}
