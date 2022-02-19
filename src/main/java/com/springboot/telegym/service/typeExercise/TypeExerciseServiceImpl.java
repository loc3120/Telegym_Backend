package com.springboot.telegym.service.typeExercise;

import com.springboot.telegym.dao.typeExercise.TypeExerciseDao;
import com.springboot.telegym.dto.TypeExerciseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
@RequiredArgsConstructor
public class TypeExerciseServiceImpl implements TypeExerciseService {

    private final TypeExerciseDao typeExerciseDao;

    @Override
    public TypeExerciseDto createOrUpdate(TypeExerciseDto typeExerciseDto) {
        return typeExerciseDao.createOrUpdate(typeExerciseDto);
    }
}
