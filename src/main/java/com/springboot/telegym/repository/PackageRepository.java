package com.springboot.telegym.repository;

import com.springboot.telegym.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<PackageEntity, String> {

    @Query("Select count(p) from PackageEntity p join TypeExercise t on " +
            "p.typeExercise.id = t.id where p.name = :name and t.typename = :typeName " +
            "and p.is_deleted = false")
    int findingExistPackage(
            @Param("name") String name, @Param("typeName") String typeName);

    @Query("Select p from PackageEntity p join TypeExercise t on " +
            "p.typeExercise.id = t.id where t.typename = :typeName " +
            "and p.is_deleted = false")
    List<PackageEntity> printAllPackage(@Param("typeName") String typeName);

}
