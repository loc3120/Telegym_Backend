package com.springboot.telegym.service.coach;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.coach.CoachDao;
import com.springboot.telegym.dto.CoachDto;
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
public class CoachServiceImpl implements CoachService {

    private final CoachDao coachDao;

    @Override
    public CoachDto createOrUpdate(CoachDto coachDto) {
        return coachDao.createOrUpdate(coachDto);
    }

    @Override
    public PageData<CoachDto> getAllCoach(StructurePageRequest structurePageRequest) {
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return coachDao.getAllCoach(pageable);
    }
}
