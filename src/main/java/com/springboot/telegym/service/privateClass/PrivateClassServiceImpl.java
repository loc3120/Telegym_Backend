package com.springboot.telegym.service.privateClass;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.privateClass.PrivateClassDao;
import com.springboot.telegym.dto.PrivateClassDto;
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
public class PrivateClassServiceImpl implements PrivateClassService {

    private final PrivateClassDao privateClassDao;

    @Override
    public void createOrUpdate(PrivateClassDto privateClassDto) {
        privateClassDao.createOrUpdate(privateClassDto);
    }

    @Override
    public PageData<PrivateClassDto> getAllPrivateClass(StructurePageRequest structurePageRequest) {
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return privateClassDao.getAllPrivateClass(pageable);
    }
}
