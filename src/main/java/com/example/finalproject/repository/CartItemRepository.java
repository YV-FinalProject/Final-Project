package com.example.finalproject.repository;

import com.example.finalproject.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Modifying
    @Query(value = "DELETE FROM CartItems " +
            "WHERE cartItemId = ?1", nativeQuery = true)
    void deleteById(Long id);
}
