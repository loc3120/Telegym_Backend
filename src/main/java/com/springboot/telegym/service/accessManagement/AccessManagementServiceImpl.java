package com.springboot.telegym.service.accessManagement;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.accessManagement.AccessManagementDao;
import com.springboot.telegym.dto.AccessManagementDto;
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
public class AccessManagementServiceImpl implements AccessManagementService {

    private final AccessManagementDao accessManagementDao;

    @Override
    public int CheckInOrCheckOut(AccessManagementDto accessManagementDto) {
        return accessManagementDao.CheckInOrCheckOut(accessManagementDto);
    }

    @Override
    public PageData<AccessManagementDto> selectEntryAndExitHistoryCustomer(StructurePageRequest structurePageRequest, String id_customer) {
        structurePageRequest.setSortProperty("updated_time");
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return accessManagementDao.selectEntryAndExitHistoryCustomer(pageable, id_customer);
    }

    @Override
    public PageData<AccessManagementDto> selectEntryAndExitHistoryGeneralClass(StructurePageRequest structurePageRequest, String id_class) {
        structurePageRequest.setSortProperty("updated_time");
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return accessManagementDao.selectEntryAndExitHistoryCustomer(pageable, id_class);
    }

    @Override
    public PageData<AccessManagementDto> listCustomerInClass(StructurePageRequest structurePageRequest, String id_class) {
        structurePageRequest.setSortProperty("id");
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return accessManagementDao.selectEntryAndExitHistoryCustomer(pageable, id_class);
    }

    @Override
    public int numberCustomerInClass(String id_class) {
        return accessManagementDao.numberCustomerInClass(id_class);
    }
}
