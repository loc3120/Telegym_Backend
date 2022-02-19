package com.springboot.telegym.service.tryingPractice;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.tryingPractice.TryingPracticeDao;
import com.springboot.telegym.dto.TryingPracticeDto;
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
public class TryingPracticeServiceImpl implements TryingPracticeService {

    private final TryingPracticeDao tryingPracticeDao;

    @Override
    public PageData<TryingPracticeDto> getAllTP(StructurePageRequest structurePageRequest, String search) {
        Pageable pageable = PageUtils.getPageable(structurePageRequest);
        search = search == null || search.isBlank() ? "%%" : "%" + search.trim().toLowerCase() + "%";
        return tryingPracticeDao.getAllTP(pageable, search);
    }

    @Override
    public void createTP(TryingPracticeDto tryingPracticeDto) {
        tryingPracticeDao.createTP(tryingPracticeDto);
    }

    @Override
    public void contactCustomer(String id) {
        tryingPracticeDao.contactCustomer(id);
    }
}
