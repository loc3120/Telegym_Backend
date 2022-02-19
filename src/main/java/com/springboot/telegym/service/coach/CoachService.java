package com.springboot.telegym.service.coach;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CoachDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface CoachService {

    CoachDto createOrUpdate(CoachDto coachDto);

    PageData<CoachDto> getAllCoach(StructurePageRequest pageRequest, String typeCoach);
}
