package com.springboot.telegym.repository;

import com.springboot.telegym.entity.TrackingProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingProgressRepository extends JpaRepository<TrackingProgress, String> {

    @Query(value = "{CALL Create_TrackingProgress(:id, :id_private_class)}",
            nativeQuery = true)
    TrackingProgress createData(@Param("id") String id, @Param("id_private_class") String id_private_class);

    @Query(value = "{CALL Select_TrackingProgress(:id_private_class)}",
            nativeQuery = true)
    List<TrackingProgress> getAllHistoryCheckin(@Param("id_private_class") String id_private_class);

}
