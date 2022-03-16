package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Customer;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    String id;

    String name;

    String phone_number;

    String email;

    Date time_enroll;

    Date time_expire;

    boolean is_expire;

    String exercise_form;

    String membershipCard;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.phone_number = customer.getPhone_number();
        this.email = customer.getEmail();
        this.time_enroll = customer.getTime_enroll();
        this.time_expire = customer.getTime_expire();
        this.is_expire = customer.is_expire();
        this.exercise_form = customer.getExercise_form();
        this.membershipCard = customer.getMembershipCard().getId();
    }
}