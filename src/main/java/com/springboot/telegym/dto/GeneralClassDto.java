package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Coach;
import com.springboot.telegym.entity.GeneralClass;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralClassDto extends BaseDto{

    String id;

    String name;

    String type;

    String description;

    int capacity;

    String practice_time;

    Coach coach;

    public GeneralClassDto(GeneralClass generalClass) {
        super(generalClass);
        this.id = generalClass.getId();
        this.name = generalClass.getName();
        this.type = generalClass.getType();
        this.description = generalClass.getDescription();
        this.capacity = generalClass.getCapacity();
        this.practice_time = generalClass.getPractice_time();
        this.coach = generalClass.getCoach();
    }
}
