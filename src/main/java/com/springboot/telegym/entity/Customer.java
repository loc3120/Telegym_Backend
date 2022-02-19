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
@Table(name = "Customer")
public class Customer extends BaseEntity{
    @Id
    private String id;

    private String name;

    private String phone_number;

    private String email;

    @ManyToMany
    @JoinTable(name = "Customer_Package", joinColumns = @JoinColumn(name = "id_customer"),
            inverseJoinColumns = @JoinColumn(name = "id_package"))
    private List<PackageEntity> packageEntityCustomerList;
}
