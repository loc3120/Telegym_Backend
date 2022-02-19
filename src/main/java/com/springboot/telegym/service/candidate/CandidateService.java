package com.springboot.telegym.service.candidate;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CandidateDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface CandidateService {

    PageData<CandidateDto> getAllCandidate(StructurePageRequest structurePageRequest, String search);

    void createCandidate(CandidateDto candidateDto);

    void candidateReview(String id, Boolean approvalValue);
}
