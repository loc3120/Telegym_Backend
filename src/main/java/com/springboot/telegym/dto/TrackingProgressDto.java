package com.springboot.telegym.dto;

import com.springboot.telegym.entity.PrivateClass;
import com.springboot.telegym.entity.TrackingProgress;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackingProgressDto {

    String id;

    Date time_checkin;

    PrivateClass privateClass;

    public TrackingProgressDto(TrackingProgress trackingProgress) {
        this.id = trackingProgress.getId();
        this.time_checkin = trackingProgress.getTime_checkin();
        this.privateClass = trackingProgress.getPrivateClass();
    }
}
