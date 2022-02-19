package com.springboot.telegym.dto;

import com.springboot.telegym.entity.TypeExercise;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeExerciseDto extends BaseDto {

    private String id;

    private String typename;

    public TypeExerciseDto(TypeExercise typeExercise) {
        super(typeExercise);
        this.id = typeExercise.getId();
        this.typename = typeExercise.getTypename();
    }
}
