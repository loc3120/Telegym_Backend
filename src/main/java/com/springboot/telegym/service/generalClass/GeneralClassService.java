package com.springboot.telegym.service.generalClass;

import com.springboot.telegym.dto.GeneralClassDto;

import java.util.List;

public interface GeneralClassService {

    void createOrUpdate(GeneralClassDto generalClassDto);

    List<GeneralClassDto> selectGC();

    List<GeneralClassDto> listNameGeneralClass(String type);

    GeneralClassDto detailGeneralClass(String id);
}
