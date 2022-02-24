package com.springboot.telegym.repository;

import com.springboot.telegym.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

//    @Modifying
//    @Procedure(procedureName = "Create_Candidate")
//    void createCandidate(@Param("id") String id, @Param("name") String name, @Param("date_of_birth") Date date_of_birth,
//                        @Param("email") String email, @Param("phone_number") String phone_number,
//                        @Param("description") String description, @Param("is_approve") boolean is_approve,
//                        @Param("reply_by") String reply_by);
}
