package com.springboot.telegym.repository;

import com.springboot.telegym.entity.AccessManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessManagementRepository extends JpaRepository<AccessManagement, String> {

    @Modifying
    @Query(value = "{CALL Checkin_AccessManagement(:id, :id_class, :id_customer)}", nativeQuery = true)
    int checkinClass(@Param("id") String id, @Param("id_class") String id_class, @Param("id_customer") String id_customer);

    @Modifying
    @Query(value = "{CALL Checkout_AccessManagement(:id_class, :id_customer)}", nativeQuery = true)
    int checkoutClass(@Param("id_class") String id_class, @Param("id_customer") String id_customer);

    @Query(value = "{CALL Identify_Checkin_Data(:id_customer)}", nativeQuery = true)
    int isCustomerCheckout(@Param("id_customer") String id_customer);

    @Procedure(procedureName = "Select_History_Customer")
    List<AccessManagement> historyCustomer(@Param("id_customer") String id_customer);

    @Procedure(procedureName = "Select_History_GeneralClass")
    List<AccessManagement> historyGeneralClass(@Param("id_class") String id_class);

    @Procedure(procedureName = "List_Customer_In_Class")
    List<Object[]> ListCustomerInClass(@Param("id_class") String id_class);

    @Query(value = "{CALL Number_Customer_In_Class(:id_class)}", nativeQuery = true)
    int NumberCustomerInClass(@Param("id_class") String id_class);
}
