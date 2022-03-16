package com.springboot.telegym.service.privateClass;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.PrivateClassDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface PrivateClassService {

    void createOrUpdate(PrivateClassDto privateClassDto);

    PageData<PrivateClassDto> getAllPrivateClass(StructurePageRequest structurePageRequest, String search);
}
