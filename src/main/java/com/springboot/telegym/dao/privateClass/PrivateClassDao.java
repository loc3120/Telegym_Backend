package com.springboot.telegym.dao.privateClass;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.PrivateClassDto;
import org.springframework.data.domain.Pageable;

public interface PrivateClassDao {

    void createOrUpdate(PrivateClassDto privateClassDto);

    PageData<PrivateClassDto> getAllPrivateClass(Pageable pageable);
}
