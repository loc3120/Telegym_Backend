package com.springboot.telegym.service.coach;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dao.coach.CoachDao;
import com.springboot.telegym.dto.CoachDto;
import com.springboot.telegym.request.PageUtils;
import com.springboot.telegym.request.StructurePageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PageData<CoachDto> getAllCoach(StructurePageRequest structurePageRequest, String search) {
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return coachDao.getAllCoach(pageable, search);
    }

    @Override
    public int ratingCoach(SearchObject searchObject) {
        return coachDao.ratingCoach(searchObject.getStr1(), searchObject.getFl1());
    }

    @Override
    public List<CoachDto> top6RatingCoach() {
        return coachDao.top6RatingCoach();
    }

    @Override
    public CoachDto coachDetail(String id) {
        return coachDao.coachDetail(id);
    }

    @Override
    public int deleteCoach(String id) {
        return coachDao.deleteCoach(id);
    }
}
