package com.springboot.telegym.service.generalClass;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.GeneralClassDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface GeneralClassService {

    void createOrUpdate(GeneralClassDto generalClassDto);

    PageData<GeneralClassDto> selectGC(StructurePageRequest structurePageRequest);
}
