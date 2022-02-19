package com.springboot.telegym.mapper;

import com.springboot.telegym.dto.CandidateDto;
import com.springboot.telegym.dto.TryingPracticeDto;
import com.springboot.telegym.dto.UserDto;
import com.springboot.telegym.entity.Candidate;
import com.springboot.telegym.entity.TryingPractice;
import com.springboot.telegym.entity.User;

public class MapperDtoAndEntity {

    public User convertToEntity(UserDto userDto) {
        User u = new User();
        u.setId(userDto.getId());
        u.setUsername(userDto.getUsername());
        u.setToken_value(userDto.getTokenValue());
        return u;
    }

    public Candidate convertToEntity(CandidateDto candidateDto) {
        Candidate c= new Candidate();
        c.setId(candidateDto.getId());
        c.setName(candidateDto.getName());
        c.setDateOfBirth(candidateDto.getDateOfBirth());
        c.setEmail(candidateDto.getEmail());
        c.setPhone_number(candidateDto.getPhone_number());
        c.setDescription(candidateDto.getDescription());
        c.setTime_sent(candidateDto.getTime_sent());
        c.set_approve(candidateDto.isApproved());
        c.setTime_reply(candidateDto.getTime_reply());
        c.setReply_by(candidateDto.getReply_by());
        return c;
    }

    public TryingPractice convertToEntity(TryingPracticeDto tryingPracticeDto) {
        TryingPractice tp = new TryingPractice();
        tp.setId(tryingPracticeDto.getId());
        tp.setName(tryingPracticeDto.getName());
        tp.setPhone_number(tryingPracticeDto.getPhone_number());
        tp.setEmail(tryingPracticeDto.getEmail());
        tp.setTime_sent(tryingPracticeDto.getTime_sent());
        tp.set_contacted(tryingPracticeDto.is_contacted());
        tp.setTime_reply(tryingPracticeDto.getTime_reply());
        tp.setReply_by(tryingPracticeDto.getReply_by());
        return tp;
    }
}
