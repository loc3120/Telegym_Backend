package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Package")
public class PackageEntity extends BaseEntity{

    @Id
    private String id;

    private String name;

    private String time_duration;

    private String description;

    private long price;

    private boolean is_deleted;

    @ManyToMany(mappedBy = "packageEntityCoachList")
    private List<Coach> coachList;

    @ManyToMany(mappedBy = "packageEntityCustomerList")
    private List<Customer> customerList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private TypeExercise typeExercise;
}
