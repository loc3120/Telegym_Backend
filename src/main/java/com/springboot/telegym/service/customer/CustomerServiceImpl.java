package com.springboot.telegym.service.customer;

import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dao.customer.CustomerDao;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    @Override
    public PageData<CustomerDto> getAllCustomer(StructurePageRequest structurePageRequest, String search) {

        Pageable pageable = PageUtils.getPageable(structurePageRequest);
        return customerDao.getAllCustomer(pageable, search);
    }

    @Override
    public void createOrUpdate(CustomerDto customerDto) {
        customerDao.createOrUpdate(customerDto);
    }
}
