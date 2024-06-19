package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getAll();
    CategoryEntity getById(Long id);
    CategoryEntity create(CategoryCreateDto categoryCreateDto);
}
