package com.springboot.telegym.dao.trackingProgress;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.MyListComparator;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.TrackingProgressDto;
import com.springboot.telegym.entity.TrackingProgress;
import com.springboot.telegym.repository.PrivateClassRepository;
import com.springboot.telegym.repository.TrackingProgressRepository;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Component
public class TrackingProgressDaoImpl implements TrackingProgressDao {


    private final TrackingProgressRepository trackingProgressRepository;

    private final PrivateClassRepository privateClassRepository;

    public TrackingProgressDaoImpl(TrackingProgressRepository trackingProgressRepository, PrivateClassRepository privateClassRepository) {
        this.trackingProgressRepository = trackingProgressRepository;
        this.privateClassRepository = privateClassRepository;
    }

    @Override
    public TrackingProgressDto createData(TrackingProgressDto trackingProgressDto) {
        if (check_session_remaining(trackingProgressDto.getPrivateClass().getId())) {
            TrackingProgress newTrackingProgress = trackingProgressRepository.createData(UUID.randomUUID().toString(), trackingProgressDto.getPrivateClass().getId());
            if (newTrackingProgress != null) {
                MessageResponse.message = "Checkin thành công";
                return convertToTrackingProgressDto(newTrackingProgress);
            }
        }
        MessageResponse.message = "Checkin thất bại";
        return null;
    }

    @Override
    public PageData<TrackingProgressDto> getAllTrackingProgress(Pageable pageable, String id_private_class) {
        List<TrackingProgress> trackingProgressList = trackingProgressRepository.getAllHistoryCheckin(id_private_class);

        trackingProgressList.sort(new MyListComparator(pageable));
        PagedListHolder<TrackingProgress> trackingProgressPage = new PagedListHolder<>(trackingProgressList);
        trackingProgressPage.setPage(pageable.getPageNumber());
        trackingProgressPage.setPageSize(pageable.getPageSize());
        List<TrackingProgressDto> trackingProgressDtoList = new ArrayList<>();
        for (TrackingProgress data : trackingProgressPage.getPageList()) {
            trackingProgressDtoList.add(convertToTrackingProgressDto(data));
        }
        return new PageData<>(trackingProgressDtoList, trackingProgressPage.getPageCount(), trackingProgressPage.getNrOfElements(), trackingProgressPage.isLastPage());
    }

    @Override
    public boolean check_session_remaining(String id_private_class) {
        return privateClassRepository.numberRemainingSessions(id_private_class) > 0;
    }

    private TrackingProgressDto convertToTrackingProgressDto (TrackingProgress trackingProgress) {
        return TrackingProgressDto.builder()
                .id(trackingProgress.getId())
                .time_checkin(trackingProgress.getTime_checkin())
                .build();
    }
}
