package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "generalclass")
public class GeneralClass extends BaseEntity {

    @Id
    private String id;

    private String name;

    private String type;

    private String description;

    private int capacity;

    private String practice_time;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coach")
    private Coach coach;
}
