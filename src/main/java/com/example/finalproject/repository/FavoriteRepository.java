package com.example.finalproject.repository;

import com.example.finalproject.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

    @Modifying
    @Query("delete from Favorite favorite where favorite.favoriteId = :id")
    void deleteById(Long id);
}
