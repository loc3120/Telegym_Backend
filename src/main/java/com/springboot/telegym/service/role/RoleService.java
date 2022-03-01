package com.springboot.telegym.service.role;

import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAll();

    int modifyPermission(SearchObject searchObject);
}
