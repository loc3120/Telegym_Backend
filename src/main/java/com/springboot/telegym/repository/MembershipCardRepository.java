package com.springboot.telegym.repository;

import com.springboot.telegym.entity.MembershipCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipCardRepository extends JpaRepository<MembershipCard, String> {

    @Procedure(procedureName = "Select_MembershipCard")
    List<MembershipCard> selectMembershipCard();

    @Modifying
    @Query(value = "{CALL Delete_MembershipCard(:id)}", nativeQuery = true)
    int deleteMembershipCard(@Param("id") String id);
}
