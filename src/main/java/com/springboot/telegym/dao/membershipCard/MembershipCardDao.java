package com.springboot.telegym.dao.membershipCard;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.MembershipCardDto;
import org.springframework.data.domain.Pageable;

public interface MembershipCardDao {

    void createOrUpdate(MembershipCardDto membershipCardDto);

    PageData<MembershipCardDto> getAllMembershipCard(Pageable pageable);

    int deleteMembershipCard(String id);
}
