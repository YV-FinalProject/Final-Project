package com.example.finalproject.repository;

import com.example.finalproject.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query("DELETE FROM CartItem cartItem " +
            "WHERE cartItem.cartItemId = :id")
    void deleteById(Long id);
}
