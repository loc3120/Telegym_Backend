package com.springboot.telegym.dto;

import com.springboot.telegym.entity.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends BaseDto {
    private String id;

    private String rolename;

    public RoleDto(Role role) {
        super(role);
        this.id = role.getId();
        this.rolename = role.getRolename();
    }
}
