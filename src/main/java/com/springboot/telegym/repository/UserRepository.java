package com.springboot.telegym.repository;

import com.springboot.telegym.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String name);

//    @Modifying
//    @Query("Update User u set u.token_value = :tokenValue, u.updated_time = :updatedTime where u.id = :id")
//    void updateJwt(@Param("tokenValue") String tokenValue, @Param("updatedTime") Date updatedTime, @Param("id") String id);

    @Modifying
    @Procedure(procedureName = "Update_Token_Value")
    void updateJwt(@Param("tokenValue") String tokenValue, @Param("id") String id);

//    @Query("Select u.username, u.name, u.role.rolename from User u where u.is_deleted = false and " +
//            "u.role.rolename in :roleAccount and u.name like :search")
//    Page<Object[]> searchUserByFilter(Pageable pageable, @Param("search") String search, @Param("roleAccount") List<String> roleAccount);

    @Procedure(procedureName = "Select_User")
    List<Object[]> searchUserFilter(@Param("Search") String search, @Param("ListRole") String listRole);

    @Query(value = "{CALL Create_User(:id, :username, :pass, :name, :is_deleted, :created_by, :updated_by, :id_role)}",
            nativeQuery = true)
    User createUser(@Param("id") String id, @Param("username") String username, @Param("pass") String pass,
                    @Param("name") String name, @Param("created_by") String created_by,
                    @Param("updated_by") String updated_by, @Param("id_role") String id_role);

    @Query(value = "{CALL Update_User(:id, :pass, :name, :updated_by, :id_role)}", nativeQuery = true)
    User updateUser(@Param("id") String id, @Param("pass") String pass, @Param("name") String name,
                       @Param("updated_by") String updated_by, @Param("id_role") String id_role);

    @Modifying
    @Query(value = "{CALL Delete_User(:id)}", nativeQuery = true)
    int deleteUser(@Param("id") String id);
}
