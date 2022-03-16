package com.springboot.telegym.service.accessManagement;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.accessManagement.AccessManagementDao;
import com.springboot.telegym.dto.AccessManagementDto;
import com.springboot.telegym.dto.CustomerDto;
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
    public PageData<AccessManagementDto> selectEntryAndExitHistoryGeneralClass(StructurePageRequest structurePageRequest, String id_generalClass) {
        structurePageRequest.setSortProperty("updated_time");
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return accessManagementDao.selectEntryAndExitHistoryGeneralClass(pageable, id_generalClass);
    }

    @Override
    public PageData<CustomerDto> listCustomerInClass(StructurePageRequest structurePageRequest, String id_generalClass) {
        structurePageRequest.setSortProperty("name");
        Pageable pageable = PageUtils.getPageable(structurePageRequest);

        return accessManagementDao.listCustomerInClass(pageable, id_generalClass);
    }

    @Override
    public int numberCustomerInClass(String id_generalClass) {
        return accessManagementDao.numberCustomerInClass(id_generalClass);
    }
}
