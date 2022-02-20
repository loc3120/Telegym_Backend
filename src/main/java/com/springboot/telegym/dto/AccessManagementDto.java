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

    String generalClass;

    String customer;

    public AccessManagementDto(AccessManagement accessManagement) {
        this.id = accessManagement.getId();
        this.time_checkin = accessManagement.getTime_checkin();
        this.is_checkout = accessManagement.is_checkout();
        this.generalClass = accessManagement.getGeneralClass().getName();
        this.customer = accessManagement.getCustomer().getName();
    }
}
