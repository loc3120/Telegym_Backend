package com.springboot.telegym.service.generalClass;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.generalClass.GeneralClassDao;
import com.springboot.telegym.dto.GeneralClassDto;
import com.springboot.telegym.request.PageUtils;
import com.springboot.telegym.request.StructurePageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PageData<GeneralClassDto> selectGC(StructurePageRequest structurePageRequest) {
        structurePageRequest.setSortProperty("name");
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return generalClassDao.getAllGC(pageable);
    }
}
