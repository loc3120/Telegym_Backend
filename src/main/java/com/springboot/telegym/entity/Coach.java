package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @ManyToMany
    @JoinTable(name = "Coach_Package", joinColumns = @JoinColumn(name = "id_coach"),
    inverseJoinColumns = @JoinColumn(name = "id_package"))
    private List<PackageEntity> packageEntityCoachList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private TypeExercise typeExercise;
}
