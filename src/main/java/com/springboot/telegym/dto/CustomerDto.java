package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Customer;
import com.springboot.telegym.entity.PackageEntity;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends BaseDto {

    String id;

    String name;

    String phone_number;

    String email;

    Set<PackageEntity> packageEntityCustomerSet;

    public CustomerDto(Customer customer) {
        super(customer);
        this.id = customer.getId();
        this.name = customer.getName();
        this.phone_number = customer.getPhone_number();
        this.email = customer.getEmail();
//        this.packageEntityCustomerSet = customer.getPackageEntityCustomerSet();
        this.setCreated_time(customer.getCreated_time());
        this.setCreated_by(customer.getCreated_by());
        this.setUpdated_time(customer.getUpdated_time());
        this.setUpdated_by(customer.getUpdated_by());
    }
}