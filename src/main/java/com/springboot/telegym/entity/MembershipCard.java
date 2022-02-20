package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "membershipcard")
public class MembershipCard {

    @Id
    private String id;

    private String cardname;

    private int level_card;

    private String description;

    private long minprice;

    private boolean is_deleted;
}
