package com.example.finalproject.repository;

import com.example.finalproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select OrderID from Orders where UserID = ?1 order by OrderID desc limit 1", nativeQuery = true)
    Order findLastOrderByUserId(Long userId);
}
