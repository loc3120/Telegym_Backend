package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Coach;
import com.springboot.telegym.entity.PackageEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto extends BaseDto{

    private String id;

    private String name;

    private Date dateOfBirth;

    private String email;

    private String phone_number;

    private String description;

    List<PackageEntity> packageEntityCoachList;

    public CoachDto(Coach coach) {
        super(coach);
        this.id = coach.getId();
        this.name = coach.getName();
        this.dateOfBirth = coach.getDateOfBirth();
        this.email = coach.getEmail();
        this.phone_number = coach.getPhone_number();
        this.description = coach.getDescription();
        this.packageEntityCoachList = coach.getPackageEntityCoachList();
        this.setCreated_time(coach.getCreated_time());
        this.setCreated_by(coach.getCreated_by());
        this.setUpdated_time(coach.getUpdated_time());
        this.setUpdated_by(coach.getUpdated_by());
    }
}
