package com.springboot.telegym.dao.packageFolder;

import com.springboot.telegym.dto.PackageDto;

import java.util.List;

public interface PackageDao {

    PackageDto createOrUpdate(PackageDto packageDto);

    boolean findingExistPackage(String name, String typeName);

    List<PackageDto> getAll(String typeName);

    void deletePackage(String id);

    boolean existedId(String id);
}
