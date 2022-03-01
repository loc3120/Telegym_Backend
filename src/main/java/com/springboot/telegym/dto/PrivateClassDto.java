package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Coach;
import com.springboot.telegym.entity.Customer;
import com.springboot.telegym.entity.PrivateClass;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateClassDto {

    String id;

    String name;

    String description;

    int number_sessions;

    int remaining_sessions;

    Date created_time;

    String created_by;

    Customer customer;

    Coach coach;

    public PrivateClassDto(PrivateClass privateClass) {
        this.id = privateClass.getId();
        this.name = privateClass.getName();
        this.description = privateClass.getDescription();
        this.number_sessions = privateClass.getNumber_sessions();
        this.remaining_sessions = privateClass.getRemaining_sessions();
        this.customer = privateClass.getCustomer();
        this.coach = privateClass.getCoach();
        setCreated_time(privateClass.getCreated_time());
        setCreated_by(privateClass.getCreated_by());
    }
}
