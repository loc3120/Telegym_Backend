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
@Table(name = "Coach")
public class Coach extends BaseEntity {

    @Id
    private String id;

    private String name;

    private Date dateOfBirth;

    private String email;

    private String phone_number;

    private String description;

    private float rating;

    private boolean is_deleted;

    public Coach(String id, String name, Date dateOfBirth, String email, String phone_number, String description,
                 float rating, boolean is_deleted) {
        super();
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone_number = phone_number;
        this.description = description;
        this.rating = rating;
        this.is_deleted = is_deleted;
    }
}
