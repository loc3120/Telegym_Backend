package com.springboot.telegym.dao.generalClass;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.dto.GeneralClassDto;
import com.springboot.telegym.entity.GeneralClass;
import com.springboot.telegym.repository.GeneralClassRepository;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GeneralClassDaoImpl implements GeneralClassDao {

    @Autowired
    private final GeneralClassRepository generalClassRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public GeneralClassDaoImpl(GeneralClassRepository generalClassRepository) {
        this.generalClassRepository = generalClassRepository;
    }

    @Override
    public void createOrUpdate(GeneralClassDto generalClassDto) {

        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();
        if (generalClassDto.getId() != null && userDetails != null) {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Update_GeneralClass");
            registerAndSetParamProcGC(query, generalClassDto, userDetails.getId());
            query.execute();
            MessageResponse.message = "Thêm đổi thông tin thành công";
        }
        else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
        }
        else {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Create_GeneralClass");
            registerAndSetParamProcGC(query, generalClassDto, userDetails.getId());
            query.execute();
            MessageResponse.message = "Thêm lớp học thành công";
        }

    }

    @Override
    public List<GeneralClassDto> getAllGC() {

        List<GeneralClass> generalClassList = generalClassRepository.selectGC();
        List<GeneralClassDto> generalClassDtoList = new ArrayList<>();

        for (GeneralClass data : generalClassList) {
            generalClassDtoList.add(convertToGeneralClassDto(data));
        }
        return generalClassDtoList;
    }

    @Override
    public List<GeneralClassDto> listNameGeneralClass(String type) {

        List<GeneralClass> generalClassList = generalClassRepository.selectAllNameOfType(type);
        List<GeneralClassDto> resultObject = new ArrayList<>();
        for (GeneralClass data : generalClassList) {
            resultObject.add(convertToGeneralClassDto(data));
        }
        return resultObject;
    }

    @Override
    public GeneralClassDto detailGeneralClass(String id) {
        GeneralClass generalClass = generalClassRepository.selectDetailGC(id);
        if (generalClass == null) {
            MessageResponse.message = "Không tìm thấy đối tượng";
            return null;
        }
        else {
            MessageResponse.message = "Tìm thành công";
            return convertToGeneralClassDto(generalClass);
        }
    }

    private void registerAndSetParamProcGC(StoredProcedureQuery query, GeneralClassDto generalClassDto, String id_user) {

        query.registerStoredProcedureParameter("id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("type", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("description", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("capacity", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("practice_time", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("id_coach", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("updated_by", String.class, ParameterMode.IN);

        query.setParameter("name", generalClassDto.getName());
        query.setParameter("type", generalClassDto.getType());
        query.setParameter("description", generalClassDto.getDescription());
        query.setParameter("capacity", generalClassDto.getCapacity());
        query.setParameter("practice_time", generalClassDto.getPractice_time());
        query.setParameter("id_coach", generalClassDto.getCoach());
        query.setParameter("updated_by", id_user);

        if (generalClassDto.getId() == null) {
            query.setParameter("id", UUID.randomUUID().toString());
            query.registerStoredProcedureParameter("created_by", String.class, ParameterMode.IN);
            query.setParameter("created_by", id_user);
        }
        else {
            query.setParameter("id", generalClassDto.getId());
        }
    }

    private GeneralClassDto convertToGeneralClassDto(GeneralClass generalClass) {
        return GeneralClassDto.builder().id(generalClass.getId())
                .name(generalClass.getName())
                .type(generalClass.getType())
                .description(generalClass.getDescription())
                .capacity(generalClass.getCapacity())
                .practice_time(generalClass.getPractice_time())
                .coach(generalClass.getCoach().getId())
                .build();
    }
}
