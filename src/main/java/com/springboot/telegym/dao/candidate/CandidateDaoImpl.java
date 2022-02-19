package com.springboot.telegym.dao.candidate;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CandidateDto;
import com.springboot.telegym.entity.Candidate;
import com.springboot.telegym.mapper.MapperDtoAndEntity;
import com.springboot.telegym.repository.CandidateRepository;
import com.springboot.telegym.request.SendMailForCandidate;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
        Root<Candidate> candidateRoot = cq.from(Candidate.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isNull(candidateRoot.get("reply_by")));
        predicates.add(cb.or(cb.like(cb.lower(candidateRoot.get("name")), search),
                cb.like(cb.lower(candidateRoot.get("email")), search)));
        cq.select(candidateRoot).where(predicates.toArray(new Predicate[0]));

        Query queryCount = entityManager.createQuery(cq);
        TypedQuery<Candidate> query = entityManager.createQuery(cq);


        query.setFirstResult( pageable.getPageSize()*pageable.getPageNumber()); // Bắt đầu từ trang 0

        query.setMaxResults(pageable.getPageSize());
        AtomicLong totalElements = new AtomicLong();
        try {
            totalElements.set(queryCount.getResultList().size());
        } catch (Exception e) {
            totalElements.set(0);
            e.printStackTrace();
        }

        List<Candidate> candidates = query.getResultList();
        List<CandidateDto> candidateDtoList = new ArrayList<>();

        if (candidates.size() > 0) {
            for (Candidate c : candidates) {
                candidateDtoList.add(convertToCandidateDto(c));
            }
        }

        AtomicInteger totalPages = new AtomicInteger();
        if (totalElements.get() > 0) {
            totalPages.set((int) (totalElements.get() % pageable.getPageSize() == 0 ?
                    totalElements.get() / pageable.getPageSize() :
                    totalElements.get() / pageable.getPageSize() + 1));
        }

        boolean hasNext = pageable.getPageNumber() < totalPages.get() - 1;
        return new PageData<>(candidateDtoList, totalPages.get(), totalElements.get(), hasNext);
    }

    @Override
    public void createCandidate(CandidateDto candidateDto) {
        candidateDto.setId(UUID.randomUUID().toString());
        candidateDto.setTime_sent(new Date());

        entityManager.persist(new MapperDtoAndEntity().convertToEntity(candidateDto));
    }

    @Override
    public void candidateReview(String id, Boolean approvalValue) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();

        if(candidate.isPresent()) {
            candidate.get().set_approve(approvalValue);
            assert userDetails != null;
            candidate.get().setReply_by(userDetails.getId());
            candidate.get().setTime_reply(new Date());
            entityManager.persist(candidate.get());

            sendMailForCandidate.sendMail(candidate.get().getName(), approvalValue);
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
