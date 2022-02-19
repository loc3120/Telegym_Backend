package com.springboot.telegym.entity;

import com.springboot.telegym.entity.idclass.CoachPackageId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Coach_Package")
@IdClass(CoachPackageId.class)
public class Coach_Package extends BaseEntity {

    @Id
    private String id_coach;

    @Id
    private String id_package;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_coach")
//    private Coach coach;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_package")
//    private PackageEntity aPackageEntity;
}
