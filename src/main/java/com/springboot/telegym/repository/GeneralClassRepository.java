package com.springboot.telegym.repository;

import com.springboot.telegym.entity.GeneralClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralClassRepository extends JpaRepository<GeneralClass, String> {

    @Procedure(procedureName = "Select_GeneralClass")
    List<GeneralClass> selectGC();

    @Query(value = "{CALL Select_All_Name_GeneralClass(:type)}", nativeQuery = true)
    List<GeneralClass> selectAllNameOfType(@Param("type") String type);

    @Query(value = "{CALL Select_Detail_GeneralClass(:id)}", nativeQuery = true)
    GeneralClass selectDetailGC(@Param("id") String id);
}
