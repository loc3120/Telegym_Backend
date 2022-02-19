package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tryingpractice")
public class TryingPractice {

    @Id
    private String id;

    private String name;

    private String phone_number;

    private String email;

    private Date time_sent;

    private boolean is_contacted;

    private Date time_reply;

    private String reply_by;
}
