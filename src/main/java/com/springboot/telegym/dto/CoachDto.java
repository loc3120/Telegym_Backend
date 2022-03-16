package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Coach;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto extends BaseDto{

    String id;

    String name;

    Date dateOfBirth;

    String email;

    String phone_number;

    String description;

    float rating;

    boolean is_deleted;

    public CoachDto(Coach coach) {
        super(coach);
        this.id = coach.getId();
        this.name = coach.getName();
        this.dateOfBirth = coach.getDateOfBirth();
        this.email = coach.getEmail();
        this.phone_number = coach.getPhone_number();
        this.description = coach.getDescription();
        this.rating = coach.getRating();
        this.is_deleted = coach.is_deleted();
        setCreated_time(coach.getCreated_time());
        setCreated_by(coach.getCreated_by());
        setUpdated_time(coach.getUpdated_time());
        setUpdated_by(coach.getUpdated_by());
    }
}
