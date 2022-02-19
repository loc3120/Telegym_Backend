package com.springboot.telegym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User extends BaseEntity {

    @Id
    private String id;

    private String name;

    private String username;

    private String pass;

    private boolean is_deleted = false;

    private String token_value;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role")
    private Role role;

    public User(String username, String pass, String name, boolean is_deleted) {
        this.username = username;
        this.pass = pass;
        this.name = name;
        this.is_deleted = is_deleted;
    }

    public User(String id, String username, String pass, String token_value) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.token_value = token_value;
    }
    public User(Date created_time, String created_by, Date updated_time, String updated_by,
                String id, String name, String username, String pass, boolean is_deleted,
                String token_value, Role role) {
        super();
        setCreated_time(created_time);
        setCreated_by(created_by);
        setUpdated_time(updated_time);
        setUpdated_by(updated_by);
        this.id = id;
        this.name = name;
        this.username = username;
        this.pass = pass;
        this.is_deleted = is_deleted;
        this.token_value = token_value;
        this.role = role;
    }
}
