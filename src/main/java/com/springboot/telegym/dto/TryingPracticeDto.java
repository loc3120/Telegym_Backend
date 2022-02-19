package com.springboot.telegym.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TryingPracticeDto {

    private String id;

    private String name;

    private String phone_number;

    private String email;

    private Date time_sent;

    private boolean is_contacted;

    private Date time_reply;

    private String reply_by;
}
