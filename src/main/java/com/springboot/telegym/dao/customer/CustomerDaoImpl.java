package com.springboot.telegym.dao.customer;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.CustomerDto;
import com.springboot.telegym.entity.Customer;
import com.springboot.telegym.request.SendPromotionalForCustomer;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Component
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SendPromotionalForCustomer sendPromotionalForCustomer;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageData<CustomerDto> getAllCustomer(Pageable pageable, String search) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("Select_Customer", Customer.class);

        query.registerStoredProcedureParameter("Search", String.class, ParameterMode.IN);
        query.setParameter("Search", search);
        query.execute();

        PagedListHolder<Customer> CustomerPage = new PagedListHolder<>((List<Customer>) query.getResultList());
        CustomerPage.setPage(pageable.getPageNumber());
        CustomerPage.setPageSize(pageable.getPageSize());
        List<CustomerDto> customerDtoList = new ArrayList<>();

        for (Customer c : CustomerPage.getPageList()) {
            customerDtoList.add(convertToCustomerDto(c));
        }

        return new PageData<>(customerDtoList, CustomerPage.getPageCount(),
                CustomerPage.getNrOfElements(), CustomerPage.isLastPage());
    }

    @Override
    public void createOrUpdate(CustomerDto customerDto) {
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();
        if (customerDto.getId() != null && userDetails != null) {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Update_Customer");
            registerAndSetParamProcCustomer(query, customerDto, userDetails.getId());
            query.execute();
        }
        else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
        }
        else {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Create_Customer");
            registerAndSetParamProcCustomer(query, customerDto, userDetails.getId());
            query.execute();
        }
    }

    private void registerAndSetParamProcCustomer(StoredProcedureQuery query, CustomerDto customerDto, String id_user) {

        query.registerStoredProcedureParameter("id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("phone_number", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("time_enroll", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("time_expire", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("exercise_form", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("updated_by", String.class, ParameterMode.IN);

        query.setParameter("name", customerDto.getName());
        query.setParameter("phone_number", customerDto.getPhone_number());
        query.setParameter("email", customerDto.getEmail());
        query.setParameter("time_enroll", customerDto.getTime_enroll());
        query.setParameter("time_expire", customerDto.getTime_expire());
        query.setParameter("exercise_form", customerDto.getExercise_form());
        query.setParameter("updated_by", id_user);

        if (customerDto.getId() == null) {
            query.setParameter("id", UUID.randomUUID().toString());
            query.registerStoredProcedureParameter("id_card", String.class, ParameterMode.IN);
            query.setParameter("id_card", customerDto.getMembershipCard());
            query.registerStoredProcedureParameter("created_by", String.class, ParameterMode.IN);
            query.setParameter("created_by", id_user);
        }
        else {
            query.setParameter("id", customerDto.getId());
            query.registerStoredProcedureParameter("is_expire", Boolean.class, ParameterMode.IN);
            query.setParameter("is_expire", customerDto.is_expire());
        }
    }

    private CustomerDto convertToCustomerDto(Customer customer) {
        return CustomerDto.builder().id(customer.getId())
                .name(customer.getName())
                .phone_number(customer.getPhone_number())
                .email(customer.getEmail())
                .time_enroll(customer.getTime_enroll())
                .time_expire(customer.getTime_expire())
                .is_expire(customer.is_expire())
                .exercise_form(customer.getExercise_form())
                .membershipCard(customer.getMembershipCard().getId())
                .build();
    }
}
