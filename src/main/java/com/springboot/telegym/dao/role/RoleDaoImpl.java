package com.springboot.telegym.dao.role;

import com.springboot.telegym.dto.RoleDto;
import com.springboot.telegym.entity.Role;
import com.springboot.telegym.repository.PermissionRepository;
import com.springboot.telegym.repository.RoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class RoleDaoImpl implements RoleDao {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    public RoleDaoImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> liRole = roleRepository.findAll();
        List<RoleDto> liRoleDto = new ArrayList<>();
        liRole.forEach(r -> liRoleDto.add(convertToRoleDto(r)));
        return liRoleDto;
    }

    @Override
    public int modifyPermission(String tableName, String permissionName) {
        return permissionRepository.modifyPermUser(tableName, permissionName);
    }

    private RoleDto convertToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .rolename(role.getRolename())
                .build();
    }
}
