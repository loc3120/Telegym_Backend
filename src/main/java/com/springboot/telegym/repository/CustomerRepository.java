package com.springboot.telegym.repository;

import com.springboot.telegym.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value = "{CALL Select_Detail_Customer(:id)}", nativeQuery = true)
    List<Object[]> selectDetailCoach(@Param("id") String id);
}
