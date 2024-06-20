package com.example.finalproject.repository;

import com.example.finalproject.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
}
