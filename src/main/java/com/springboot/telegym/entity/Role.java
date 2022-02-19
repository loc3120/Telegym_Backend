package com.springboot.telegym.entity;

import lombok.AllArgsConstructor;
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
@Table(name = "Role")
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    private String id;

    private String rolename; //Các loại role
}
