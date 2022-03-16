package com.springboot.telegym.repository;

import com.springboot.telegym.entity.PrivateClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateClassRepository extends JpaRepository<PrivateClass, String> {

    @Query(value = "{CALL Select_PrivateClass(:search)}",
            nativeQuery = true)
    List<PrivateClass> selectPrivateClass(@Param("search") String search);

    @Query(value = "{CALL Check_Session_Remain_PrivateClass(:id_private_class)}",
            nativeQuery = true)
    int numberRemainingSessions(@Param("id_private_class") String id_private_class);
}
