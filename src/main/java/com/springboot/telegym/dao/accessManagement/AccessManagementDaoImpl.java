package com.springboot.telegym.dao.accessManagement;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.MyListComparator;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.AccessManagementDto;
import com.springboot.telegym.entity.AccessManagement;
import com.springboot.telegym.repository.AccessManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class AccessManagementDaoImpl implements AccessManagementDao {

    @Autowired
    private final AccessManagementRepository accessManagementRepository;

    public AccessManagementDaoImpl(AccessManagementRepository accessManagementRepository) {
        this.accessManagementRepository = accessManagementRepository;
    }

    @Override
    public int CheckInOrCheckOut(AccessManagementDto accessManagementDto) {

        int resultCheckout = accessManagementRepository.isCustomerCheckout(accessManagementDto.getId());
        int lineSuccess;
        if (resultCheckout == 0) {
            lineSuccess = accessManagementRepository.checkinClass(accessManagementDto.getId(),
                    accessManagementDto.getGeneralClass(), accessManagementDto.getCustomer());
            if (lineSuccess > 0)
                MessageResponse.message = "Checkin thành công";
            else
                MessageResponse.message = "Checkin thất bại";

        } else {
            lineSuccess = accessManagementRepository.checkoutClass(accessManagementDto.getGeneralClass(),
                    accessManagementDto.getCustomer());
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
    public PageData<AccessManagementDto> listCustomerInClass(Pageable pageable, String id_class) {
        List<AccessManagement> accessManagementList = accessManagementRepository.ListCustomerInClass(id_class);
        return fomulaReturnAccessManagementPageData(accessManagementList, pageable);
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
                .generalClass(accessManagement.getGeneralClass().getName())
                .customer(accessManagement.getCustomer().getName())
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
