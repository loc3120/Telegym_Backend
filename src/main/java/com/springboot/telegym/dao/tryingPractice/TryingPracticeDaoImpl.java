package com.springboot.telegym.dao.tryingPractice;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.TryingPracticeDto;
import com.springboot.telegym.entity.TryingPractice;
import com.springboot.telegym.mapper.MapperDtoAndEntity;
import com.springboot.telegym.repository.TryingPracticeRepository;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
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
public class TryingPracticeDaoImpl implements TryingPracticeDao {

    private final TryingPracticeRepository tryingPracticeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TryingPracticeDaoImpl(TryingPracticeRepository tryingPracticeRepository) {
        this.tryingPracticeRepository = tryingPracticeRepository;
    }

    @Override
    public PageData<TryingPracticeDto> getAllTP(Pageable pageable, String search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TryingPractice> cq = cb.createQuery(TryingPractice.class);
        Root<TryingPractice> tpRoot = cq.from(TryingPractice.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isNull(tpRoot.get("reply_by")));
        predicates.add(cb.or(cb.like(cb.lower(tpRoot.get("name")), search),
                cb.like(cb.lower(tpRoot.get("email")), search)));
        cq.select(tpRoot).where(predicates.toArray(new Predicate[0]));

        Query queryCount = entityManager.createQuery(cq);
        TypedQuery<TryingPractice> query = entityManager.createQuery(cq);


        query.setFirstResult(pageable.getPageSize() * pageable.getPageNumber()); // Bắt đầu từ trang 0

        query.setMaxResults(pageable.getPageSize());
        AtomicLong totalElements = new AtomicLong();
        try {
            totalElements.set(queryCount.getResultList().size());
        } catch (Exception e) {
            totalElements.set(0);
            e.printStackTrace();
        }

        List<TryingPractice> tps = query.getResultList();
        List<TryingPracticeDto> tpDtoList = new ArrayList<>();

        if (tps.size() > 0) {
            for (TryingPractice c : tps) {
                tpDtoList.add(convertToTryingPracticeDto(c));
            }
        }

        AtomicInteger totalPages = new AtomicInteger();
        if (totalElements.get() > 0) {
            totalPages.set((int) (totalElements.get() % pageable.getPageSize() == 0 ?
                    totalElements.get() / pageable.getPageSize() :
                    totalElements.get() / pageable.getPageSize() + 1));
        }

        boolean hasNext = pageable.getPageNumber() < totalPages.get() - 1;
        return new PageData<>(tpDtoList, totalPages.get(), totalElements.get(), hasNext);
    }

    @Override
    public void createTP(TryingPracticeDto tryingPracticeDto) {
        tryingPracticeDto.setId(UUID.randomUUID().toString());
        tryingPracticeDto.setTime_sent(new Date());

        entityManager.persist(new MapperDtoAndEntity().convertToEntity(tryingPracticeDto));
    }

    @Override
    public void contactCustomer(String id) {
        Optional<TryingPractice> tpEntity = tryingPracticeRepository.findById(id);
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();

        if(tpEntity.isPresent()) {
            tpEntity.get().set_contacted(true);
            assert userDetails != null;
            tpEntity.get().setReply_by(userDetails.getId());
            tpEntity.get().setTime_reply(new Date());
            entityManager.persist(tpEntity.get());
        }
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
