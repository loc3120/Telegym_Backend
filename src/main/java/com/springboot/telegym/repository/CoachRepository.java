package com.springboot.telegym.repository;

import com.springboot.telegym.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, String> {

    Optional<Coach> findByEmail(String email);

    @Procedure(procedureName = "Select_Coach")
    List<Coach> selectCoach();

    @Procedure(procedureName = "Create_Coach")
    Coach createCoach(@Param("id") String id, @Param("name") String name, @Param("date_of_birth") Date date_of_birth,
                      @Param("email") String email, @Param("phone_number") String phone_number,
                      @Param("description") String description, @Param("created_by") String created_by,
                      @Param("updated_by") String updated_by);

    @Procedure(procedureName = "Update_Coach")
    Coach updateCoach(@Param("id") String id, @Param("name") String name, @Param("date_of_birth") Date date_of_birth,
                      @Param("email") String email, @Param("phone_number") String phone_number,
                      @Param("description") String description, @Param("updated_by") String updated_by);
}
