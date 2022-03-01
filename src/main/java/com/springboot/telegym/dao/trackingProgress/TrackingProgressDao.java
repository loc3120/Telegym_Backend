package com.springboot.telegym.dao.trackingProgress;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.TrackingProgressDto;
import org.springframework.data.domain.Pageable;

public interface TrackingProgressDao {

    TrackingProgressDto createData(TrackingProgressDto trackingProgressDto);

    PageData<TrackingProgressDto> getAllTrackingProgress(Pageable pageable, String id_private_class);

    boolean check_session_remaining (String id_private_class);
}
