package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Customer;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends BaseDto {

    String id;

    String name;

    String phone_number;

    String email;

    Date time_enroll;

    Date time_expire;

    boolean is_expire;

    private String exercise_form;

    String membershipCard;

    public CustomerDto(Customer customer) {
        super(customer);
        this.id = customer.getId();
        this.name = customer.getName();
        this.phone_number = customer.getPhone_number();
        this.email = customer.getEmail();
        this.time_enroll = customer.getTime_enroll();
        this.time_expire = customer.getTime_expire();
        this.is_expire = customer.is_expire();
        this.exercise_form = customer.getExercise_form();
        this.membershipCard = customer.getMembershipCard().getId();
        setCreated_time(customer.getCreated_time());
        setCreated_by(customer.getCreated_by());
        setUpdated_time(customer.getUpdated_time());
        setUpdated_by(customer.getUpdated_by());
    }
}