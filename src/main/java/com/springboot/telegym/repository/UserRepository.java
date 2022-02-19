package com.springboot.telegym.repository;

import com.springboot.telegym.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String name);

    Boolean existsByUsername(String username);

    @Modifying
    @Query("Update User u set u.token_value = :tokenValue, u.updated_time = :updatedTime where u.id = :id")
    void updateJwt(@Param("tokenValue") String tokenValue, @Param("updatedTime") Date updatedTime, @Param("id") String id);

    @Query("Select u.username, u.name, u.role.rolename from User u where u.is_deleted = false and " +
            "u.role.rolename in :roleAccount and u.name like :search")
    Page<Object[]> searchUserByFilter(Pageable pageable, @Param("search") String search, @Param("roleAccount") List<String> roleAccount);
}
