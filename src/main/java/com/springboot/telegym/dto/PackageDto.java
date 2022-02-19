package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Coach;
import com.springboot.telegym.entity.Customer;
import com.springboot.telegym.entity.PackageEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageDto extends BaseDto {

    private String id;

    private String name;

    private String time_duration;

    private String description;

    private long price;

    private boolean is_deleted;

    private List<Coach> coachList;

    private List<Customer> customerList;

    private String typeExercise;

    public PackageDto(PackageEntity p) {
        super(p);
        this.id = p.getId();
        this.name = p .getName();
        this.time_duration = p.getTime_duration();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.is_deleted = p.is_deleted();
        this.coachList = p.getCoachList();
        this.customerList = p.getCustomerList();
        this.typeExercise = p.getTypeExercise().getTypename();
        this.setCreated_time(p.getCreated_time());
        this.setCreated_by(p.getCreated_by());
        this.setUpdated_time(p.getUpdated_time());
        this.setUpdated_by(p.getUpdated_by());
    }
}
