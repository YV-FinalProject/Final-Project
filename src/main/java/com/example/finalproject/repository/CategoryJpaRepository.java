package com.example.finalproject.repository;

import com.example.finalproject.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
Optional<CategoryEntity> findByName(String name);
}
