package com.springboot.telegym.entity;

import com.springboot.telegym.entity.idclass.CustomerPackageId;
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
@Table(name = "Customer_Package")
@IdClass(CustomerPackageId.class)
public class Customer_Package extends  BaseEntity{

    @Id
    private String id_customer;

    @Id
    private String id_package;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_package")
//    private PackageEntity aPackageEntity;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_customer")
//    private Customer customer;
}
