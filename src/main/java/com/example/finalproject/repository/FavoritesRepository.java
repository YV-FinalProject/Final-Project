package com.example.finalproject.repository;

import com.example.finalproject.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites,Long> {

}
