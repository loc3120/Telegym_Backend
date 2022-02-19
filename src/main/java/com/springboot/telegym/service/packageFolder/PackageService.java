package com.springboot.telegym.service.packageFolder;

import com.springboot.telegym.dto.PackageDto;

import java.util.List;

public interface PackageService {

    PackageDto createOrUpdate(PackageDto packageDto);

    List<PackageDto> getAll(String typeName);

    void deletePackage(String id);

    boolean existedId(String id);
}
