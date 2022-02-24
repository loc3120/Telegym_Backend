package com.springboot.telegym.service.candidate;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.candidate.CandidateDao;
import com.springboot.telegym.dto.CandidateDto;
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
public class CandidateServiceImpl implements CandidateService {

    private final CandidateDao candidateDao;

    @Override
    public PageData<CandidateDto> getAllCandidate(StructurePageRequest structurePageRequest, String search) {
        Pageable pageable = PageUtils.getPageable(structurePageRequest);
        search = search == null || search.isBlank() ? "%%" : "%" + search.trim().toLowerCase() + "%";
        return candidateDao.getAllCandidate(pageable, search);
    }

    @Override
    public int createCandidate(CandidateDto candidateDto) {
        return candidateDao.createCandidate(candidateDto);
    }

    @Override
    public String candidateReview(String id, Boolean approvalValue) {
        return candidateDao.candidateReview(id, approvalValue);
    }
}
