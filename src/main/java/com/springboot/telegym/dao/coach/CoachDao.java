package com.springboot.telegym.dao.coach;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CoachDto;
import org.springframework.data.domain.Pageable;

public interface CoachDao {

    PageData<CoachDto> getAllCoach(Pageable pageable);

    CoachDto createOrUpdate(CoachDto coachDto);

//    CoachDto findByEmail(String email);
}
