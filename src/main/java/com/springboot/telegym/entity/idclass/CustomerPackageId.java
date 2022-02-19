package com.springboot.telegym.entity.idclass;

import java.io.Serializable;

public class CustomerPackageId implements Serializable {

    private String id_customer;

    private String id_package;

    public CustomerPackageId(String id_customer, String id_package) {
        this.id_customer = id_customer;
        this.id_package = id_package;
    }
}
