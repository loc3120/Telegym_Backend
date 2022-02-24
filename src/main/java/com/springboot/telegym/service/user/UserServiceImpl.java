package com.springboot.telegym.service.user;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dao.user.UserDao;
import com.springboot.telegym.dto.UserDto;
import com.springboot.telegym.request.PageUtils;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.request.UserFilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public UserDto createOrUpdate(UserDto userDto) {
        return userDao.createOrUpdate(userDto);
    }

    @Override
    public UserDto getById(String id) {
        return userDao.getById(id);
    }

    @Override
    public int deleteById(String id) {
        return userDao.deleteById(id);
    }

    @Override
    public UserDto login(SearchObject searchObject) {
        return userDao.login(searchObject);
    }

    @Override
    public PageData<UserDto> filterUser(StructurePageRequest structurePageRequest, UserFilterRequest request) {
        Pageable pageable = PageUtils.getPageable(structurePageRequest);
        List<String> listRole = new ArrayList<>();
        if (request.getRoleName() == null) {
            listRole.add("ROLE_ADMIN");
            listRole.add("ROLE_STAFF");
        }
        else {
            listRole.add(request.getRoleName());
        }
        request.setSearch(request.getSearch() == null || request.getSearch().isBlank() ? "%%" :
                "%" + request.getSearch().trim().toLowerCase() + "%");
        return userDao.filterUser(pageable, request.getSearch(), listRole);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadByUsername(username);
    }
}
