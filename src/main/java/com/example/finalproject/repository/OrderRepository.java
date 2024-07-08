package com.example.finalproject.repository;

import com.example.finalproject.entity.*;
import com.example.finalproject.entity.enums.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.transaction.annotation.*;

import java.sql.*;
import java.util.*;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = " +
            "CASE " +
            "WHEN o.status = 'CREATED' THEN 'PENDING_PAYMENT' " +
            "WHEN o.status = 'PENDING_PAYMENT' THEN 'PAID' " +
            "WHEN o.status = 'PAID' THEN 'ON_THE_WAY' " +
            "WHEN o.status = 'ON_THE_WAY' THEN 'DELIVERED' " +
            "ELSE o.status END, " +
            "o.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE o.createdAt > :startDate " +
            "AND o.status IN (:statuses)")
    Integer updateOrderStatuses(Timestamp startDate, @Param("statuses") List<Status> statuses);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status != 'DELIVERED' AND o.status != 'CANCELED' AND o.createdAt > :startDate")
    Integer countUndeliveredOrders(Timestamp startDate);
}
