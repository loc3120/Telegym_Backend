package com.springboot.telegym.dao.coach;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.MyListComparator;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CoachDto;
import com.springboot.telegym.entity.Coach;
import com.springboot.telegym.repository.CoachRepository;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Component
public class CoachDaoImpl implements CoachDao {

    private final CoachRepository coachRepository;

    public CoachDaoImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public PageData<CoachDto> getAllCoach(Pageable pageable, String search) {
        List<Coach> coachList = coachRepository.selectCoach(search);
        coachList.sort(new MyListComparator(pageable));

        PagedListHolder<Coach> coachPage = new PagedListHolder<>(coachList);
        coachPage.setPage(pageable.getPageNumber());
        coachPage.setPageSize(pageable.getPageSize());
        List<CoachDto> coachDtoList = new ArrayList<>();
        for (Coach data : coachPage.getPageList()) {
            coachDtoList.add(new CoachDto(data));
        }
        return new PageData<>(coachDtoList, coachPage.getPageCount(), coachPage.getNrOfElements(), coachPage.isLastPage());
    }

    @Override
    public CoachDto createOrUpdate(CoachDto coachDto) {

        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();

        if (coachDto.getId() != null && userDetails != null) {
            Coach updatedCoach = coachRepository.updateCoach(coachDto.getId(), coachDto.getName(),
                    coachDto.getDateOfBirth(), coachDto.getEmail(), coachDto.getPhone_number(),
                    coachDto.getDescription(), userDetails.getId());
            if(updatedCoach != null) {
                MessageResponse.message = "Update thành công";
                return new CoachDto(updatedCoach);
            }
            else {
                MessageResponse.message = "Update thất bại";
                return null;
            }
        }
        else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
            return null;
        }
        else {
            Coach newCoach = coachRepository.createCoach(UUID.randomUUID().toString(), coachDto.getName(),
                    coachDto.getDateOfBirth(), coachDto.getEmail(), coachDto.getPhone_number(),
                    coachDto.getDescription(), userDetails.getId(), userDetails.getId());
            if (newCoach != null) {
                MessageResponse.message = "Thêm HLV thành công";
                return new CoachDto(newCoach);
            }
            MessageResponse.message = "Thêm HLV thất bại";
            return null;
        }
    }

    @Override
    public int ratingCoach(String id, float rating) {
        int lineSuccess = coachRepository.ratingCoach(id, rating);
        if (lineSuccess > 0) {
            MessageResponse.message = "Chấm điểm thành công";
        }
        else {
            MessageResponse.message = "Chấm điểm thất bại";
        }
        return lineSuccess;
    }

    @Override
    public List<CoachDto> top6RatingCoach() {
        List<Coach> coachList = coachRepository.top6RatingCoach();
        List<CoachDto> coachDtoList = new ArrayList<>();
        for (Coach c : coachList) {
            coachDtoList.add(convertToCoachDto(c));
        }
        return coachDtoList;
    }

    @Override
    public CoachDto coachDetail(String id) {
        List<Object[]> resultList = coachRepository.selectDetailCoach(id);
        if (resultList == null) {
            MessageResponse.message = "Không tìm thấy huấn luyện viên";
            return null;
        }
        else {
            CoachDto coachDto = new CoachDto();
            for (Object[] data : resultList) {
                coachDto.setId(data[0].toString());
                coachDto.setName(data[1].toString());
            }
            MessageResponse.message = "Tìm thấy huấn luyện viên thành công";
            return coachDto;
        }
    }

    @Override
    public int deleteCoach(String id) {
        int lineSuccess = coachRepository.deleteCoach(id);
        if (lineSuccess > 0) {
            MessageResponse.message = "Xoá thành công";
        }
        else {
            MessageResponse.message = "Xoá thất bại";
        }
        return lineSuccess;
    }

//    @Override
//    public CoachDto findByEmail(String email) {
//        Coach coach;
//        Optional<Coach> coachEntity = coachRepository.findByEmail(email);
//        if (coachEntity.isPresent()) {
//            coach = coachEntity.get();
//            return new CoachDto(coach);
//        }
//        return null;
//    }

    private CoachDto convertToCoachDto(Coach coach) {
        return CoachDto.builder()
                .id(coach.getId())
                .name(coach.getName())
                .dateOfBirth(coach.getDateOfBirth())
                .email(coach.getEmail())
                .phone_number(coach.getPhone_number())
                .description(coach.getDescription())
                .rating(coach.getRating())
                .is_deleted(coach.is_deleted())
                .build();
    }
}
