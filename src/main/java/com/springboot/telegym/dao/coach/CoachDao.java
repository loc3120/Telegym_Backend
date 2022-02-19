package com.springboot.telegym.dao.coach;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CoachDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoachDao {

    PageData<CoachDto> getAllCoach(Pageable pageable, List<String> typeCoach);

    CoachDto createOrUpdate(CoachDto coachDto);

    CoachDto findByEmail(String email);
}
