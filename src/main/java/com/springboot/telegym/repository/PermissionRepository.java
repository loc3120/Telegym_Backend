package com.springboot.telegym.repository;

import com.springboot.telegym.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PermissionRepository extends JpaRepository<Permission, String> {

    @Query(value = "{CALL List_Table()}", nativeQuery = true)
    List<Object> listTableInDB();

    @Query(value = "{CALL Count_Permission()}", nativeQuery = true)
    int countDataPermission();

    @Query(value = "{CALL Finding_name_Permission()}", nativeQuery = true)
    List<Object> findingName();

    @Modifying
    @Query(value = "{CALL Modify_Perm_User(:tableName, :permissionName)}", nativeQuery = true)
    int modifyPermUser(@Param("tableName") String tableName, @Param("permissionName") String permissionName);
}
