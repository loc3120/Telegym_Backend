package com.springboot.telegym.dao.membershipCard;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.MyListComparator;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.MembershipCardDto;
import com.springboot.telegym.entity.MembershipCard;
import com.springboot.telegym.repository.MembershipCardRepository;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Component
public class MembershipCardDaoImpl implements MembershipCardDao {

    @Autowired
    private final MembershipCardRepository membershipCardRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public MembershipCardDaoImpl(MembershipCardRepository membershipCardRepository) {
        this.membershipCardRepository = membershipCardRepository;
    }

    @Override
    public void createOrUpdate(MembershipCardDto membershipCardDto) {
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();
        if (membershipCardDto.getId() != null && userDetails != null) {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Update_MembershipCard");
            registerAndSetParamProcMembershipCard(query, membershipCardDto);
            query.execute();
        }
        else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
        }
        else {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Create_MembershipCard");
            registerAndSetParamProcMembershipCard(query, membershipCardDto);
            query.execute();
        }
    }

    @Override
    public PageData<MembershipCardDto> getAllMembershipCard(Pageable pageable) {
        List<MembershipCard> membershipCardList = membershipCardRepository.selectMembershipCard();
        membershipCardList.sort(new MyListComparator(pageable));

        PagedListHolder<MembershipCard> membershipCardPage = new PagedListHolder<>(membershipCardList);
        membershipCardPage.setPage(pageable.getPageNumber());
        membershipCardPage.setPageSize(pageable.getPageSize());
        List<MembershipCardDto> membershipCardDtoList = new ArrayList<>();
        for (MembershipCard data : membershipCardPage.getPageList()) {
            membershipCardDtoList.add(convertToMembershipCardDto(data));
        }
        return new PageData<>(membershipCardDtoList, membershipCardPage.getPageCount(), membershipCardPage.getNrOfElements(), membershipCardPage.isLastPage());
    }

    @Override
    public int deleteMembershipCard(String id) {
        return membershipCardRepository.deleteMembershipCard(id);
    }

    private void registerAndSetParamProcMembershipCard(StoredProcedureQuery query, MembershipCardDto membershipCardDto) {

        query.registerStoredProcedureParameter("id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("cardname", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("level_card", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("description", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("minprice", Long.class, ParameterMode.IN);

        query.setParameter("cardname", membershipCardDto.getCardname());
        query.setParameter("level_card", membershipCardDto.getLevel_card());
        query.setParameter("description", membershipCardDto.getDescription());
        query.setParameter("minprice", membershipCardDto.getMinprice());

        if (membershipCardDto.getId() == null) {
            query.setParameter("id", UUID.randomUUID().toString());
        }
        else {
            query.setParameter("id", membershipCardDto.getId());
        }
    }

    private MembershipCardDto convertToMembershipCardDto(MembershipCard membershipCard) {
        return MembershipCardDto.builder().id(membershipCard.getId())
                .cardname(membershipCard.getCardname())
                .level_card(membershipCard.getLevel_card())
                .description(membershipCard.getDescription())
                .minprice(membershipCard.getMinprice())
                .is_deleted(membershipCard.is_deleted())
                .build();
    }
}
