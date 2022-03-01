package com.springboot.telegym.dao.role;

import com.springboot.telegym.dto.RoleDto;

import java.util.List;

public interface RoleDao {
    List<RoleDto> getAll();

    int modifyPermission(String tableName, String permissionName);
}
