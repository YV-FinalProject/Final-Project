package com.example.finalproject.mapper;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel= "spring")
public interface CategoryMapper {
    CategoryDto toDto(CategoryEntity entity);
    CategoryEntity toEntity(CategoryDto dto);
    CategoryEntity createDtoToEntity(CategoryCreateDto categoryCreateDto);
}
