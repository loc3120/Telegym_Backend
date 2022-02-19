package com.springboot.telegym.dao.packageFolder;

import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.dto.PackageDto;
import com.springboot.telegym.dto.enums.TypeExerciseEnum;
import com.springboot.telegym.entity.PackageEntity;
import com.springboot.telegym.entity.TypeExercise;
import com.springboot.telegym.repository.PackageRepository;
import com.springboot.telegym.repository.TypeExerciseRepository;
import com.springboot.telegym.security.SecurityUser;
import com.springboot.telegym.security.UserDetailsImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Component
public class PackageDaoImpl implements PackageDao {

    private final PackageRepository packageRepository;

    private final TypeExerciseRepository typeExerciseRepository;

    public PackageDaoImpl(PackageRepository packageRepository, TypeExerciseRepository typeExerciseRepository) {
        this.packageRepository = packageRepository;
        this.typeExerciseRepository = typeExerciseRepository;
    }

    @Override
    public PackageDto createOrUpdate(PackageDto packageDto) {

        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();
        String transmissionType = packageDto.getTypeExercise();
        TypeExercise typeExercise;

        if ("gym".equals(transmissionType)) {
            typeExercise = typeExerciseRepository.findByTypename(TypeExerciseEnum.GYM.getType())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }
        else if ("yoga".equals(transmissionType)){
            typeExercise = typeExerciseRepository.findByTypename(TypeExerciseEnum.YOGA.getType())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }
        else {
            typeExercise = typeExerciseRepository.findByTypename(transmissionType.trim().toUpperCase())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }
        if (packageDto.getId() != null && userDetails != null) {
            Optional<PackageEntity> addingPackageEntity = packageRepository.findById(packageDto.getId());
            if (addingPackageEntity.isPresent()) {
                PackageEntity updatedPackage;
                updatedPackage = addingPackageEntity.get();
                updatedPackage.setName(packageDto.getName());
                updatedPackage.setTime_duration(packageDto.getTime_duration());
                updatedPackage.setDescription(packageDto.getDescription());
                updatedPackage.setPrice(packageDto.getPrice());
                updatedPackage.setTypeExercise(typeExercise);
                updatedPackage.setUpdated_time(new Date());
                updatedPackage.setUpdated_by(userDetails.getId());
                packageRepository.save(updatedPackage);
                return new PackageDto(updatedPackage);
            }
            else {
                MessageResponse.message = "Không tìm thấy đối tượng";
                return null;
            }
        }
        else if (userDetails == null) {
            MessageResponse.message = "Không xác định người dùng";
            return null;
        }
        else {
            boolean checkNameExist = findingExistPackage(packageDto.getName(), transmissionType);
            if (checkNameExist) {
                return null;
            }
            else {
                PackageEntity newPackage = new PackageEntity();
                newPackage.setId(UUID.randomUUID().toString());
                newPackage.setName(packageDto.getName());
                newPackage.setTime_duration(packageDto.getTime_duration());
                newPackage.setDescription(packageDto.getDescription());
                newPackage.setPrice(packageDto.getPrice());
                newPackage.set_deleted(false);
                newPackage.setTypeExercise(typeExercise);
                newPackage.setCreated_time(new Date());
                newPackage.setUpdated_time(new Date());
                newPackage.setCreated_by(userDetails.getId());
                newPackage.setUpdated_by(userDetails.getId());
                packageRepository.save(newPackage);
                return new PackageDto(newPackage);
            }
        }
    }

    @Override
    public boolean findingExistPackage(String name, String typeName) {
        int countQuery = packageRepository.findingExistPackage(name, typeName);
        System.out.println(countQuery);
        return countQuery != 0;
    }

    @Override
    public List<PackageDto> getAll(String typeName) {
        List<PackageEntity> allPackages = packageRepository.printAllPackage(typeName);
        List<PackageDto> packageDtoList = new ArrayList<>();
        for (PackageEntity p : allPackages) {
            packageDtoList.add(new PackageDto(p));
        }
        return packageDtoList;
    }

    @Override
    public void deletePackage(String id) {
        UserDetailsImpl userDetails = SecurityUser.identifyCurrentUser();
        Optional<PackageEntity> pe = packageRepository.findById(id);
        if (pe.isPresent()) {
            pe.get().set_deleted(true);
            pe.get().setUpdated_time(new Date());

            assert userDetails != null;
            pe.get().setUpdated_by(userDetails.getId());
        }
    }

    @Override
    public boolean existedId(String id) {
        return packageRepository.existsById(id);
    }
}
