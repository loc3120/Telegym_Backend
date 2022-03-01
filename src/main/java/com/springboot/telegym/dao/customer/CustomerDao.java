package com.springboot.telegym.dao.customer;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CustomerDto;
import org.springframework.data.domain.Pageable;

public interface CustomerDao {

    PageData<CustomerDto> getAllCustomer(Pageable pageable, String search);

    void createOrUpdate(CustomerDto customerDto);
}
