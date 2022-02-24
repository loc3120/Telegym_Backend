package com.springboot.telegym.dao.candidate;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CandidateDto;
import org.springframework.data.domain.Pageable;

public interface CandidateDao {

    PageData<CandidateDto> getAllCandidate(Pageable pageable, String search);

    int createCandidate(CandidateDto candidateDto);

    String candidateReview(String id, Boolean approvalValue);
}
