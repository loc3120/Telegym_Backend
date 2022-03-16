package com.springboot.telegym.service.customer;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CustomerDto;
import com.springboot.telegym.request.StructurePageRequest;

public interface CustomerService {

    PageData<CustomerDto> getAllCustomer(StructurePageRequest structurePageRequest, String search);

    void createOrUpdate(CustomerDto customerDto);

    CustomerDto customerDetail(String id);
}
