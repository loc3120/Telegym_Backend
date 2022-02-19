package com.springboot.telegym.service.user;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.UserDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.request.UserFilterRequest;

public interface UserService {

//    Page<UserDto> getPage(StructurePageRequest structurePageRequest);

    UserDto createOrUpdate(UserDto userDto);

    UserDto getById(String id);

    UserDto findByUsername(String username);

    boolean existById(String id);

    void deleteById(String id);

    UserDto login(SearchObject searchObject);

    PageData<UserDto> filterUser(StructurePageRequest structurePageRequest, UserFilterRequest request);
}
