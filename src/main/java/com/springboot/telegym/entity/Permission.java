package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Permission")
public class Permission {

    @Id
    private String id;

    private String table_name;

    private String permission_name;

    private Date created_time;

    private Date updated_time;

    @ManyToMany
    @JoinTable(name = "permission_role", joinColumns = @JoinColumn(name = "id_role"), inverseJoinColumns = @JoinColumn(name = "id_permission"))
    private Set<Role> roleSet;

    public Permission(String id, String table_name, String permission_name, Date created_time, Date updated_time) {
        this.id = id;
        this.table_name = table_name;
        this.permission_name = permission_name;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }
}
