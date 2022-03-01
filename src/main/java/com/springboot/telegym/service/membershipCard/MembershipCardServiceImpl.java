package com.springboot.telegym.service.membershipCard;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.membershipCard.MembershipCardDao;
import com.springboot.telegym.dto.MembershipCardDto;
import com.springboot.telegym.request.PageUtils;
import com.springboot.telegym.request.StructurePageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
@RequiredArgsConstructor
public class MembershipCardServiceImpl implements MembershipCardService{

    private final MembershipCardDao membershipCardDao;

    @Override
    public void createOrUpdate(MembershipCardDto membershipCardDto) {
        membershipCardDao.createOrUpdate(membershipCardDto);
    }

    @Override
    public PageData<MembershipCardDto> getAllMembershipCard(StructurePageRequest structurePageRequest) {

        structurePageRequest.setSortProperty("minprice");
        structurePageRequest.setSortOrder("asc");

        Pageable pageable = PageUtils.getPageable(structurePageRequest);
        return membershipCardDao.getAllMembershipCard(pageable);
    }

    @Override
    public int deleteMembershipCard(String id) {
        return membershipCardDao.deleteMembershipCard(id);
    }
}
