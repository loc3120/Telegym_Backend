package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    private Date created_time;

    private String created_by;

    private Date updated_time;

    private String updated_by;
}
