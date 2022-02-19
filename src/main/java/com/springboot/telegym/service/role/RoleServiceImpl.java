package com.springboot.telegym.service.role;

import com.springboot.telegym.dao.role.RoleDao;
import com.springboot.telegym.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Override
    public List<RoleDto> getAll() {
        return roleDao.getAll();
    }
}
