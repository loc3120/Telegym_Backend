package com.springboot.telegym.repository;

import com.springboot.telegym.entity.MembershipCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipCardRepository extends JpaRepository<MembershipCard, String> {
}
