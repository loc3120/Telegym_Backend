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

import java.util.ArrayList;
import java.util.List;

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
    public PageData<CoachDto> getAllCoach(StructurePageRequest structurePageRequest, String typeCoach) {
        Pageable pageable = PageUtils.getPageable(structurePageRequest);
        List<String> typeList = new ArrayList<>();
        if (typeCoach == null || typeCoach.isBlank() || typeCoach.isEmpty()) {
            typeList.add("YOGA");
            typeList.add("GYM");
        }
        else {
            typeList.add(typeCoach);
        }

        return coachDao.getAllCoach(pageable, typeList);
    }
}
