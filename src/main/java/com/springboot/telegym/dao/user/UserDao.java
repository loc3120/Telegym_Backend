package com.springboot.telegym.dao.user;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserDao {

//    Page<UserDto> getPage(Pageable pageable);

    UserDto createOrUpdate(UserDto userDto);

    UserDto getById(String id);

    UserDto findByUsername(String username);

    boolean existById(String id);

    void deleteById(String id);

    UserDto login(SearchObject searchObject);

    UserDetails loadByUsername(String username);

    PageData<UserDto> filterUser(Pageable pageable, String search, List<String> roleAccount);
}
