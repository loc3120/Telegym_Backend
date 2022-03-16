package com.springboot.telegym.service.generalClass;

import com.springboot.telegym.dao.generalClass.GeneralClassDao;
import com.springboot.telegym.dto.GeneralClassDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Service
@Transactional
@RequiredArgsConstructor
public class GeneralClassServiceImpl implements GeneralClassService {

    private final GeneralClassDao generalClassDao;

    @Override
    public void createOrUpdate(GeneralClassDto generalClassDto) {
        generalClassDao.createOrUpdate(generalClassDto);
    }

    @Override
    public List<GeneralClassDto> selectGC() {
        return generalClassDao.getAllGC();
    }

    @Override
    public List<GeneralClassDto> listNameGeneralClass(String type) {
        return generalClassDao.listNameGeneralClass(type);
    }

    @Override
    public GeneralClassDto detailGeneralClass(String id) {
        return generalClassDao.detailGeneralClass(id);
    }
}
