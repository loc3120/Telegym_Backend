package com.springboot.telegym.dao.typeExercise;

import com.springboot.telegym.dto.TypeExerciseDto;

public interface TypeExerciseDao {

    TypeExerciseDto createOrUpdate(TypeExerciseDto typeExerciseDto);
}
