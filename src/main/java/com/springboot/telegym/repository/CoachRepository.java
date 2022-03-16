package com.springboot.telegym.repository;

import com.springboot.telegym.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, String> {

    Optional<Coach> findByEmail(String email);

    @Procedure(procedureName = "Select_Coach")
    List<Coach> selectCoach(@Param("search") String search);

    @Procedure(procedureName = "Create_Coach")
    Coach createCoach(@Param("id") String id, @Param("name") String name, @Param("date_of_birth") Date date_of_birth,
                      @Param("email") String email, @Param("phone_number") String phone_number,
                      @Param("description") String description, @Param("created_by") String created_by,
                      @Param("updated_by") String updated_by);

    @Procedure(procedureName = "Update_Coach")
    Coach updateCoach(@Param("id") String id, @Param("name") String name, @Param("date_of_birth") Date date_of_birth,
                      @Param("email") String email, @Param("phone_number") String phone_number,
                      @Param("description") String description, @Param("updated_by") String updated_by);

    @Modifying
    @Query(value = "{CALL Rating_Coach(:id, :rating)}", nativeQuery = true)
    int ratingCoach(@Param("id") String id, @Param("rating") float rating);

    @Procedure(procedureName = "Select_Top6_Rating_Coach")
    List<Coach> top6RatingCoach();

    @Query(value = "{CALL Select_Detail_Coach(:id_GeneralClass)}", nativeQuery = true)
    List<Object[]> selectDetailCoach(@Param("id_GeneralClass") String id_GeneralClass);

    @Modifying
    @Query(value = "{CALL Delete_Coach(:id)}", nativeQuery = true)
    int deleteCoach(@Param("id") String id);
}
