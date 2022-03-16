package com.springboot.telegym.dto;

import com.springboot.telegym.entity.AccessManagement;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessManagementDto {

    String id;

    Date time_checkin;

    boolean is_checkout;

    Date time_checkout;

    Date updated_time;

    String id_generalClass;

    String id_customer;

    public AccessManagementDto(AccessManagement accessManagement) {
        this.id = accessManagement.getId();
        this.time_checkin = accessManagement.getTime_checkin();
        this.is_checkout = accessManagement.is_checkout();
        this.time_checkout = accessManagement.getTime_checkout();
        this.updated_time = accessManagement.getUpdated_time();
        this.id_generalClass = accessManagement.getGeneralClass().getId();
        this.id_generalClass = accessManagement.getCustomer().getId();
    }
}
