package com.springboot.telegym.service.membershipCard;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.MembershipCardDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface MembershipCardService {

    void createOrUpdate(MembershipCardDto membershipCardDto);

    PageData<MembershipCardDto> getAllMembershipCard(StructurePageRequest structurePageRequest);

    int deleteMembershipCard(String id);
}
