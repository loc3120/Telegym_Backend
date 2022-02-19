package com.springboot.telegym.dao.coach;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CoachDto;
import com.springboot.telegym.entity.Coach;
import com.springboot.telegym.repository.CoachRepository;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Component
public class CoachDaoImpl implements CoachDao {

    private final CoachRepository coachRepository;

    public CoachDaoImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public PageData<CoachDto> getAllCoach(Pageable pageable, List<String> typeCoach) {
        Page<Coach> coachPage = coachRepository.printAllCoach(pageable, typeCoach);

        List<CoachDto> coachDtoList = new ArrayList<>();
        for (Coach c : coachPage.getContent()) {
            coachDtoList.add(convertToCoachDto(c));
        }
        return new PageData<>(coachDtoList, coachPage.getTotalPages(),
                coachPage.getTotalElements(), coachPage.hasNext());
    }

    @Override
    public CoachDto createOrUpdate(CoachDto coachDto) {

        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();

        if (coachDto.getId() != null && userDetails != null) {
            Optional<Coach> addingCoach = coachRepository.findById(coachDto.getId());
            if (addingCoach.isPresent()) {
                Coach updatedCoach;
                updatedCoach = addingCoach.get();
                updatedCoach.setName(coachDto.getName());
                updatedCoach.setDateOfBirth(coachDto.getDateOfBirth());
                updatedCoach.setEmail(coachDto.getEmail());
                updatedCoach.setPhone_number(coachDto.getPhone_number());
                updatedCoach.setPackageEntityCoachList(coachDto.getPackageEntityCoachList());
                updatedCoach.setUpdated_time(new Date());
                updatedCoach.setUpdated_by(userDetails.getId());
                coachRepository.save(updatedCoach);
                return new CoachDto(updatedCoach);
            }
            else {
                MessageResponse.message = "Không tìm thấy đối tượng hoặc không xác định người dùng";
                return null;
            }
        }
        else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
            return null;
        }
        else {
            CoachDto checkEmailExist = findByEmail(coachDto.getEmail());
            if (checkEmailExist != null) {
                MessageResponse.message = "Tên email trùng";
                return null;
            }
            else {
                Coach newCoach = new Coach();
                newCoach.setId(UUID.randomUUID().toString());
                newCoach.setName(coachDto.getName());
                newCoach.setDateOfBirth(coachDto.getDateOfBirth());
                newCoach.setEmail(coachDto.getEmail());
                newCoach.setPhone_number(coachDto.getPhone_number());
                newCoach.setDescription(coachDto.getDescription());
                newCoach.setPackageEntityCoachList(coachDto.getPackageEntityCoachList());
                newCoach.setCreated_time(new Date());
                newCoach.setUpdated_time(new Date());
                newCoach.setCreated_by(userDetails.getId());
                newCoach.setUpdated_by(userDetails.getId());
                coachRepository.save(newCoach);
                return new CoachDto(newCoach);
            }
        }
    }

    @Override
    public CoachDto findByEmail(String email) {
        Coach coach;
        Optional<Coach> coachEntity = coachRepository.findByEmail(email);
        if (coachEntity.isPresent()) {
            coach = coachEntity.get();
            return new CoachDto(coach);
        }
        return null;
    }

    private CoachDto convertToCoachDto(Coach coach) {
        return CoachDto.builder()
                .id(coach.getId())
                .name(coach.getName())
                .dateOfBirth(coach.getDateOfBirth())
                .email(coach.getEmail())
                .phone_number(coach.getPhone_number())
                .description(coach.getDescription())
                .build();
    }
}
