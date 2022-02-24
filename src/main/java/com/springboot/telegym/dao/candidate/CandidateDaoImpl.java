package com.springboot.telegym.dao.candidate;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CandidateDto;
import com.springboot.telegym.entity.Candidate;
import com.springboot.telegym.repository.CandidateRepository;
import com.springboot.telegym.request.SendMailForCandidate;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Component
public class CandidateDaoImpl implements CandidateDao {

    private final CandidateRepository candidateRepository;

    @Autowired
    private SendMailForCandidate sendMailForCandidate;

    @PersistenceContext
    private EntityManager entityManager;

    public CandidateDaoImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public PageData<CandidateDto> getAllCandidate(Pageable pageable, String search) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("Select_Candidate", Candidate.class);

        query.registerStoredProcedureParameter("Search", String.class, ParameterMode.IN);
        query.setParameter("Search", search);
        query.execute();

        PagedListHolder<Candidate> candidatePage = new PagedListHolder<>((List<Candidate>) query.getResultList());
        candidatePage.setPage(pageable.getPageNumber());
        candidatePage.setPageSize(pageable.getPageSize());
        List<CandidateDto> candidateDtoList = new ArrayList<>();

        for (Candidate c : candidatePage.getPageList()) {
            candidateDtoList.add(convertToCandidateDto(c));
        }

        return new PageData<>(candidateDtoList, candidatePage.getPageCount(),
                candidatePage.getNrOfElements(), candidatePage.isLastPage());
    }

    @Override
    public int createCandidate(CandidateDto candidateDto) {

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Create_Candidate");

        query.registerStoredProcedureParameter("id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("date_of_birth", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("phone_number", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("description", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("is_approve", Boolean.class, ParameterMode.IN);

        query.setParameter("id", UUID.randomUUID().toString());
        query.setParameter("name", candidateDto.getName());
        query.setParameter("date_of_birth", candidateDto.getDateOfBirth());
        query.setParameter("email", candidateDto.getEmail());
        query.setParameter("phone_number", candidateDto.getPhone_number());
        query.setParameter("description", candidateDto.getDescription());
        query.setParameter("is_approve", false);

        query.execute();

        return query.getUpdateCount();
    }

    @Override
    public String candidateReview(String id, Boolean approvalValue) {
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Review_Candidate");

        query.registerStoredProcedureParameter("id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("reply_by", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("approvalValue", Boolean.class, ParameterMode.IN);

        query.setParameter("id", id);
        assert userDetails != null;
        query.setParameter("reply_by", userDetails.getId());
        query.setParameter("approvalValue", approvalValue);

        query.execute();

        try {
            String candidateName = (String) query.getSingleResult();
            sendMailForCandidate.sendMail(candidateName, approvalValue);
            return MessageResponse.message = "Duyệt bài thành công";
        } catch (NoResultException nre) {
            return MessageResponse.message = "Không tìm thấy id bài ứng tuyển";
        }
    }

    private CandidateDto convertToCandidateDto(Candidate candidate) {
        return CandidateDto.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .dateOfBirth(candidate.getDateOfBirth())
                .email(candidate.getEmail())
                .phone_number(candidate.getPhone_number())
                .description(candidate.getDescription())
                .build();
    }
}
