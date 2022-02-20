package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer extends BaseEntity{
    @Id
    private String id;

    private String name;

    private String phone_number;

    private String email;

    private Date time_enroll;

    private Date time_expire;

    private boolean is_expire;

    private String exercise_form;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_card")
    private MembershipCard membershipCard;
}
