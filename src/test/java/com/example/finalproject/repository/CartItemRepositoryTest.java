package com.example.finalproject.repository;

import com.example.finalproject.entity.CartItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    void deleteById() {
        Long cartItemId = 5L;

        cartItemRepository.deleteById(cartItemId);

        CartItem deletedCartItem = cartItemRepository.findById(cartItemId).orElse(null);
        assertNull(deletedCartItem);
    }
}