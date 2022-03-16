package com.springboot.telegym.dao.accessManagement;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.MyListComparator;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.AccessManagementDto;
import com.springboot.telegym.dto.CustomerDto;
import com.springboot.telegym.entity.AccessManagement;
import com.springboot.telegym.repository.AccessManagementRepository;
import com.springboot.telegym.repository.MembershipCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Component
public class AccessManagementDaoImpl implements AccessManagementDao {

    @Autowired
    private final AccessManagementRepository accessManagementRepository;

    @Autowired
    private final MembershipCardRepository membershipCardRepository;

    public AccessManagementDaoImpl(AccessManagementRepository accessManagementRepository, MembershipCardRepository membershipCardRepository) {
        this.accessManagementRepository = accessManagementRepository;
        this.membershipCardRepository = membershipCardRepository;
    }

    @Override
    public int CheckInOrCheckOut(AccessManagementDto accessManagementDto) {

        int resultCheckout = accessManagementRepository.isCustomerCheckout(accessManagementDto.getId_customer());
        int lineSuccess;
        if (resultCheckout == 0) {
            lineSuccess = accessManagementRepository.checkinClass(UUID.randomUUID().toString(),
                    accessManagementDto.getId_generalClass(), accessManagementDto.getId_customer());
            if (lineSuccess > 0)
                MessageResponse.message = "Checkin thành công";
            else
                MessageResponse.message = "Checkin thất bại";

        } else {
            lineSuccess = accessManagementRepository.checkoutClass(accessManagementDto.getId_generalClass(),
                    accessManagementDto.getId_customer());
            if (lineSuccess > 0)
                MessageResponse.message = "Checkout thành công";
            else
                MessageResponse.message = "Checkout thất bại";
        }
        return lineSuccess;
    }

    @Override
    public PageData<AccessManagementDto> selectEntryAndExitHistoryCustomer(Pageable pageable, String id_customer) {

        List<AccessManagement> accessManagementList = accessManagementRepository.historyCustomer(id_customer);
        return fomulaReturnAccessManagementPageData(accessManagementList, pageable);
    }

    @Override
    public PageData<AccessManagementDto> selectEntryAndExitHistoryGeneralClass(Pageable pageable, String id_class) {

        List<AccessManagement> accessManagementList = accessManagementRepository.historyGeneralClass(id_class);
        return fomulaReturnAccessManagementPageData(accessManagementList, pageable);
    }

    @Override
    public PageData<CustomerDto> listCustomerInClass(Pageable pageable, String id_class) {
        List<Object[]> objectList = accessManagementRepository.ListCustomerInClass(id_class);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Object[] data : objectList) {
            CustomerDto c = new CustomerDto();
            c.setName((String) data[0]);
            c.setPhone_number((String) data[1]);
            c.setEmail((String) data[2]);
            c.setTime_enroll((Date) data[3]);
            c.setTime_expire((Date) data[4]);
            c.set_expire((boolean) data[5]);
            c.setExercise_form((String) data[6]);
            c.setMembershipCard((String) data[7]);
            customerDtoList.add(c);
        }
        customerDtoList.sort(new MyListComparator(pageable));

        PagedListHolder<CustomerDto> customerPage = new PagedListHolder<>(customerDtoList);
        customerPage.setPage(pageable.getPageNumber());
        customerPage.setPageSize(pageable.getPageSize());
//        List<CustomerDto> customerDtoList = new ArrayList<>();
//        for (Customer data : customerPage.getPageList()) {
//            customerDtoList.add(convertToCustomerDto(data));
//        }
        return new PageData<>(customerDtoList, customerPage.getPageCount(), customerPage.getNrOfElements(), customerPage.isLastPage());
    }

    @Override
    public int numberCustomerInClass(String id_class) {
        return accessManagementRepository.NumberCustomerInClass(id_class);
    }

    private AccessManagementDto convertToAccessManagementDto(AccessManagement accessManagement) {
        return AccessManagementDto.builder().id(accessManagement.getId())
                .time_checkin(accessManagement.getTime_checkin())
                .is_checkout(accessManagement.is_checkout())
                .time_checkout(accessManagement.getTime_checkout())
                .updated_time(accessManagement.getUpdated_time())
                .id_generalClass(accessManagement.getGeneralClass().getId())
                .id_customer(accessManagement.getCustomer().getId())
                .build();
    }


    private PageData<AccessManagementDto> fomulaReturnAccessManagementPageData(
            List<AccessManagement> accessManagementList, Pageable pageable) {
        accessManagementList.sort(new MyListComparator(pageable));

        PagedListHolder<AccessManagement> accessManagementPage = new PagedListHolder<>(accessManagementList);
        accessManagementPage.setPage(pageable.getPageNumber());
        accessManagementPage.setPageSize(pageable.getPageSize());
        List<AccessManagementDto> accessManagementDtoList = new ArrayList<>();
        for (AccessManagement data : accessManagementPage.getPageList()) {
            accessManagementDtoList.add(convertToAccessManagementDto(data));
        }
        return new PageData<>(accessManagementDtoList, accessManagementPage.getPageCount(), accessManagementPage.getNrOfElements(), accessManagementPage.isLastPage());
    }
}
