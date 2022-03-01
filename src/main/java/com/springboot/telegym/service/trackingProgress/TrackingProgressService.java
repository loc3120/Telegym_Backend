package com.springboot.telegym.service.trackingProgress;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.TrackingProgressDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface TrackingProgressService {

    TrackingProgressDto createData(TrackingProgressDto trackingProgressDto);

    PageData<TrackingProgressDto> getAllTrackingProgress(StructurePageRequest structurePageRequest, String id_private_class);
}
