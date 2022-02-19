package com.springboot.telegym.dao.typeExercise;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.dto.TypeExerciseDto;
import com.springboot.telegym.entity.TypeExercise;
import com.springboot.telegym.repository.TypeExerciseRepository;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Component
public class TypeExerciseDaoImpl implements TypeExerciseDao {

    private final TypeExerciseRepository typeExerciseRepository;

//    public MessageResponse responseContext;

    public TypeExerciseDaoImpl(TypeExerciseRepository typeExerciseRepository) {
        this.typeExerciseRepository = typeExerciseRepository;
    }

    @Override
    public TypeExerciseDto createOrUpdate(TypeExerciseDto typeExerciseDto) {
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();

        if (typeExerciseDto.getId() != null && userDetails!= null) {
            Optional<TypeExercise> addingTE = typeExerciseRepository.findById(typeExerciseDto.getId());

            String upperCaseTypeName = typeExerciseDto.getTypename().trim().toUpperCase();

            if (addingTE.isPresent()) {
                if (addingTE.get().getTypename().equals(upperCaseTypeName)) {
                    MessageResponse.message = "Tên mới và cũ giống nhau";
                    return null;
                }
                TypeExercise updatedTE;
                updatedTE = addingTE.get();
                updatedTE.setTypename(typeExerciseDto.getTypename().trim().toUpperCase());
                updatedTE.setUpdated_time(new Date());
                updatedTE.setUpdated_by(userDetails.getId());
                typeExerciseRepository.save(updatedTE);
                return new TypeExerciseDto(updatedTE);
            }
            else {
                MessageResponse.message = "Không tìm thấy id";
                return null;
            }
        }
        else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
            return null;
        }
        else {
            TypeExercise newTE = new TypeExercise();
            newTE.setId(UUID.randomUUID().toString());
            newTE.setTypename(typeExerciseDto.getTypename().trim().toUpperCase());
            newTE.setCreated_time(new Date());
            newTE.setUpdated_time(new Date());
            newTE.setCreated_by(userDetails.getId());
            newTE.setUpdated_by(userDetails.getId());
            typeExerciseRepository.save(newTE);
            return new TypeExerciseDto(newTE);
        }
    }
}
