package com.springboot.telegym.dao.role;

import com.springboot.telegym.dto.RoleDto;
import com.springboot.telegym.entity.Role;
import com.springboot.telegym.repository.RoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class RoleDaoImpl implements RoleDao {

    private final RoleRepository roleRepository;

    public RoleDaoImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> liRole = roleRepository.findAll();
        List<RoleDto> liRoleDto = new ArrayList<>();
        liRole.forEach(r -> liRoleDto.add(convertToRoleDto(r)));
        return liRoleDto;
    }

    private RoleDto convertToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .rolename(role.getRolename())
                .build();
    }
}
