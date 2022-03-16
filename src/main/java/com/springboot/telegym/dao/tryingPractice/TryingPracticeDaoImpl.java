package com.springboot.telegym.dao.tryingPractice;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.TryingPracticeDto;
import com.springboot.telegym.entity.TryingPractice;
import com.springboot.telegym.repository.TryingPracticeRepository;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.*;

@Transactional
@Component
public class TryingPracticeDaoImpl implements TryingPracticeDao {

    private final TryingPracticeRepository tryingPracticeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TryingPracticeDaoImpl(TryingPracticeRepository tryingPracticeRepository) {
        this.tryingPracticeRepository = tryingPracticeRepository;
    }

    @Override
    public PageData<TryingPracticeDto> getAllTP(Pageable pageable, String search) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("Select_TP", TryingPractice.class);

        query.registerStoredProcedureParameter("Search", String.class, ParameterMode.IN);
        query.setParameter("Search", search);
        query.execute();

        PagedListHolder<TryingPractice> tryingPracticePage = new PagedListHolder<>((List<TryingPractice>) query.getResultList());
        tryingPracticePage.setPage(pageable.getPageNumber());
        tryingPracticePage.setPageSize(pageable.getPageSize());
        List<TryingPracticeDto> tryingPracticeDtoList = new ArrayList<>();

        for (TryingPractice tp : tryingPracticePage.getPageList()) {
            tryingPracticeDtoList.add(convertToTryingPracticeDto(tp));
        }

        return new PageData<>(tryingPracticeDtoList, tryingPracticePage.getPageCount(),
                tryingPracticePage.getNrOfElements(), tryingPracticePage.isLastPage());
    }

    @Override
    public int createTP(TryingPracticeDto tryingPracticeDto) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Create_TP");

        query.registerStoredProcedureParameter("id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("phone_number", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("is_contacted", Boolean.class, ParameterMode.IN);

        query.setParameter("id", UUID.randomUUID().toString());
        query.setParameter("name", tryingPracticeDto.getName());
        query.setParameter("phone_number", tryingPracticeDto.getPhone_number());
        query.setParameter("email", tryingPracticeDto.getEmail());
        query.setParameter("is_contacted", false);

        query.execute();

        return query.getUpdateCount();
    }

    @Override
    public String contactCustomer(String id, boolean is_contacted) {
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Contact_Customer");

        query.registerStoredProcedureParameter("id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("reply_by", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("is_contacted", Boolean.class, ParameterMode.IN);

        query.setParameter("id", id);
        assert userDetails != null;
        query.setParameter("reply_by", userDetails.getId());
        query.setParameter("is_contacted", is_contacted);

        query.execute();

        if(query.getUpdateCount() > 0) {
            return MessageResponse.message = "Liên hệ thành công";
        }
        return MessageResponse.message = "Liên hệ thất bại";
    }

    private TryingPracticeDto convertToTryingPracticeDto(TryingPractice t) {
        return TryingPracticeDto.builder()
                .id(t.getId())
                .name(t.getName())
                .phone_number(t.getPhone_number())
                .email(t.getEmail())
                .time_sent(t.getTime_sent())
                .is_contacted(t.is_contacted())
                .time_reply(t.getTime_reply())
                .reply_by(t.getReply_by())
                .build();
    }
}
