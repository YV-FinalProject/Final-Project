package com.example.finalproject.repository;

import com.example.finalproject.entity.Favorite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void deleteById() {

        Long favoriteId = 5L;

        favoriteRepository.deleteById(favoriteId);

        Favorite deletedFavorite = favoriteRepository.findById(favoriteId).orElse(null);
        assertNull(deletedFavorite);
    }
}