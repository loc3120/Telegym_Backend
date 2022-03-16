package com.springboot.telegym.dao.privateClass;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.MyListComparator;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.dto.PrivateClassDto;
import com.springboot.telegym.entity.PrivateClass;
import com.springboot.telegym.repository.PrivateClassRepository;
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
import java.util.List;
import java.util.UUID;

@Transactional
@Component
public class PrivateClassDaoImpl implements PrivateClassDao {

    @Autowired
    private final PrivateClassRepository privateClassRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PrivateClassDaoImpl(PrivateClassRepository privateClassRepository) {
        this.privateClassRepository = privateClassRepository;
    }

    @Override
    public void createOrUpdate(PrivateClassDto privateClassDto) {
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();
        if (privateClassDto.getId() != null && userDetails != null) {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Update_PrivateClass");
            registerAndSetParamProcPrivateClass(query, privateClassDto, userDetails.getId());
            query.execute();
            MessageResponse.message = "Update lớp thành công";
        } else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
        } else {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Create_PrivateClass");
            registerAndSetParamProcPrivateClass(query, privateClassDto, userDetails.getId());
            query.execute();
            MessageResponse.message = "Thêm lớp thành công";
        }
    }

    @Override
    public PageData<PrivateClassDto> getAllPrivateClass(Pageable pageable, String search) {
        List<PrivateClass> privateClassList = privateClassRepository.selectPrivateClass(search);
        privateClassList.sort(new MyListComparator(pageable));

        PagedListHolder<PrivateClass> privateClassPage = new PagedListHolder<>(privateClassList);
        privateClassPage.setPage(pageable.getPageNumber());
        privateClassPage.setPageSize(pageable.getPageSize());
        List<PrivateClassDto> privateClassDtoList = new ArrayList<>();
        for (PrivateClass data : privateClassPage.getPageList()) {
            privateClassDtoList.add(convertToPrivateClassDto(data));
        }
        return new PageData<>(privateClassDtoList, privateClassPage.getPageCount(), privateClassPage.getNrOfElements(), privateClassPage.isLastPage());
    }

    private void registerAndSetParamProcPrivateClass(StoredProcedureQuery query, PrivateClassDto privateClassDto, String id_user) {
        query.registerStoredProcedureParameter("id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("description", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("number_sessions", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("id_coach", String.class, ParameterMode.IN);

        query.setParameter("name", privateClassDto.getName());
        query.setParameter("description", privateClassDto.getDescription());
        query.setParameter("number_sessions", privateClassDto.getNumber_sessions());
        query.setParameter("id_coach", privateClassDto.getCoach());

        if (privateClassDto.getId() == null) {
            query.registerStoredProcedureParameter("created_by", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("id_customer", String.class, ParameterMode.IN);
            query.setParameter("id", UUID.randomUUID().toString());
            query.setParameter("created_by", id_user);
            query.setParameter("id_customer", privateClassDto.getCustomer());
        } else {
            query.registerStoredProcedureParameter("remaining_sessions", Integer.class, ParameterMode.IN);
            query.setParameter("id", privateClassDto.getId());
            query.setParameter("remaining_sessions", privateClassDto.getRemaining_sessions());
        }
    }

    private PrivateClassDto convertToPrivateClassDto(PrivateClass privateClass) {
        return PrivateClassDto.builder().id(privateClass.getId())
                .name(privateClass.getName())
                .description(privateClass.getDescription())
                .number_sessions(privateClass.getNumber_sessions())
                .remaining_sessions(privateClass.getRemaining_sessions())
                .created_time(privateClass.getCreated_time())
                .customer(privateClass.getCustomer().getId())
                .coach(privateClass.getCoach().getId())
                .build();
    }
}
