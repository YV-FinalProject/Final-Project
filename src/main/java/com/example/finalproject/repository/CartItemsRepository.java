package com.example.finalproject.repository;

import com.example.finalproject.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
}
