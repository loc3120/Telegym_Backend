package com.springboot.telegym.dao.generalClass;

import com.springboot.telegym.dto.GeneralClassDto;

import java.util.List;

public interface GeneralClassDao {

    void createOrUpdate(GeneralClassDto generalClassDto);

    List<GeneralClassDto> getAllGC();

    List<GeneralClassDto> listNameGeneralClass(String type);

    GeneralClassDto detailGeneralClass(String id);
}
