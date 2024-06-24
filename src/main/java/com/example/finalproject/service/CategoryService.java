package com.example.finalproject.service;


import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.CategoryRequestDto;
import com.example.finalproject.dto.CategoryResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    public List<CategoryResponseDto> getCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return MapperUtil.convertList(categoryList, mappers::convertToCategoryResponseDto);
    }


    public void deleteCategoryById(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    public void insertCategories(CategoryRequestDto categoryRequestDto) {

        Category category = mappers.convertToCategory(categoryRequestDto);
        category.setCategoryId(0L);
        categoryRepository.save(category);
    }

    public void updateCategory(CategoryRequestDto categoryRequestDto, Long id) {
        if (id > 0) {
            Category category = categoryRepository.findById(id).orElse(null);
            if (category != null) {
                category.setName(categoryRequestDto.getName());
                categoryRepository.save(category);
            } else {
                throw new DataNotFoundInDataBaseException("Data not found in database.");
            }
        } else {
            throw new InvalidValueExeption("The value you entered is not valid."); }
        }
}
