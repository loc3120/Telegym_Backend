package com.springboot.telegym.dao.accessManagement;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.AccessManagementDto;
import org.springframework.data.domain.Pageable;

public interface AccessManagementDao {

    int CheckInOrCheckOut(AccessManagementDto accessManagementDto);

    PageData<AccessManagementDto> selectEntryAndExitHistoryCustomer(Pageable pageable, String id_customer);

    PageData<AccessManagementDto> selectEntryAndExitHistoryGeneralClass(Pageable pageable, String id_class);

    PageData<AccessManagementDto> listCustomerInClass(Pageable pageable, String id_class);

    int numberCustomerInClass(String id_class);
}
