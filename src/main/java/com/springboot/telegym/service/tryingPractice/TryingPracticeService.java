package com.springboot.telegym.service.tryingPractice;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.TryingPracticeDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface TryingPracticeService {

    PageData<TryingPracticeDto> getAllTP(StructurePageRequest structurePageRequest, String search);

    int createTP(TryingPracticeDto tryingPracticeDto);

    String contactCustomer(String id, boolean is_contacted);
}
