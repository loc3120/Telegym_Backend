package com.springboot.telegym.service.packageFolder;

import com.springboot.telegym.dao.packageFolder.PackageDao;
import com.springboot.telegym.dto.PackageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Service
@Transactional
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageDao packageDao;

    @Override
    public PackageDto createOrUpdate(PackageDto packageDto) {
        return packageDao.createOrUpdate(packageDto);
    }

    @Override
    public List<PackageDto> getAll(String typeName) {
        return packageDao.getAll(typeName);
    }

    @Override
    public void deletePackage(String id) {
        packageDao.deletePackage(id);
    }

    @Override
    public boolean existedId(String id) {
        return packageDao.existedId(id);
    }
}
