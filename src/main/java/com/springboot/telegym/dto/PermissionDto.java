package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Role;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

    private String id;

    private String table_name;

    private String permission_name;

    private Date created_time;

    private Date updated_time;

    private Set<Role> roleSet;
}
