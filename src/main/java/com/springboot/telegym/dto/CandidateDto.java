package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Candidate;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto {

    private String id;

    private String name;

    private Date dateOfBirth;

    private String email;

    private String phone_number;

    private String description;

    private Date time_sent;

    private boolean isApproved;

    private Date time_reply;

    private String reply_by;

    public CandidateDto( Candidate candidate ) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.dateOfBirth = candidate.getDateOfBirth();
        this.email = candidate.getEmail();
        this.phone_number = candidate.getPhone_number();
        this.description = candidate.getDescription();
        this.time_sent = candidate.getTime_sent();
        this.isApproved = candidate.is_approve();
        this.time_reply = candidate.getTime_reply();
        this.reply_by = candidate.getReply_by();
    }
}
