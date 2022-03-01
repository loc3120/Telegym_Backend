package com.springboot.telegym.service.trackingProgress;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.trackingProgress.TrackingProgressDao;
import com.springboot.telegym.dto.TrackingProgressDto;
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
public class TrackingProgressServiceImpl implements TrackingProgressService{

    private final TrackingProgressDao trackingProgressDao;

    @Override
    public TrackingProgressDto createData(TrackingProgressDto trackingProgressDto) {
        return trackingProgressDao.createData(trackingProgressDto);
    }

    @Override
    public PageData<TrackingProgressDto> getAllTrackingProgress(StructurePageRequest structurePageRequest, String id_private_class) {
        structurePageRequest.setSortProperty("time_checkin");
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return trackingProgressDao.getAllTrackingProgress(pageable, id_private_class);
    }
}
