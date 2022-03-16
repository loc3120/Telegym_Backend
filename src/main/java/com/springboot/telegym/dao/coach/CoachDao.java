package com.springboot.telegym.dao.coach;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CoachDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoachDao {

    PageData<CoachDto> getAllCoach(Pageable pageable, String search);

    CoachDto createOrUpdate(CoachDto coachDto);

    int ratingCoach(String id, float rating);

    List<CoachDto> top6RatingCoach();

    CoachDto coachDetail(String id);

    int deleteCoach(String id);
}
