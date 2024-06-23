package com.example.finalproject.mapper;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.entity.Category;

public interface CategoryMapper {
    CategoryDto toDto(Category entity);
    Category toEntity(CategoryDto dto);
    Category createDtoToEntity(CategoryCreateDto categoryCreateDto);
}
