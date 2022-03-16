package com.springboot.telegym.service.coach;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.CoachDto;
import com.springboot.telegym.request.StructurePageRequest;

import java.util.List;

public interface CoachService {

    CoachDto createOrUpdate(CoachDto coachDto);

    PageData<CoachDto> getAllCoach(StructurePageRequest pageRequest, String search);

    int ratingCoach(SearchObject searchObject);

    List<CoachDto> top6RatingCoach();

    CoachDto coachDetail(String id);

    int deleteCoach(String id);
}
