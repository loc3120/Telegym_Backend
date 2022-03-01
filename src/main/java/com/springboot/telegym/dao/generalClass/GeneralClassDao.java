package com.springboot.telegym.dao.generalClass;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.GeneralClassDto;
import org.springframework.data.domain.Pageable;

public interface GeneralClassDao {

    void createOrUpdate(GeneralClassDto generalClassDto);

    PageData<GeneralClassDto> getAllGC(Pageable pageable);
}
