package com.springboot.telegym.service.accessManagement;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.AccessManagementDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface AccessManagementService {

    int CheckInOrCheckOut(AccessManagementDto accessManagementDto);

    PageData<AccessManagementDto> selectEntryAndExitHistoryCustomer(StructurePageRequest structurePageRequest, String id_customer);

    PageData<AccessManagementDto> selectEntryAndExitHistoryGeneralClass(StructurePageRequest structurePageRequest, String id_class);

    PageData<AccessManagementDto> listCustomerInClass(StructurePageRequest structurePageRequest, String id_class);

    int numberCustomerInClass(String id_class);
}
