package com.example.finalproject.service;


import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    public List<CategoryDto> getCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return MapperUtil.convertList(categoryList, mappers::convertToCategoryDto);
    }


    public void deleteCategoryById(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    public void insertCategories(CategoryDto categoryDto) {

        categoryDto.setCategoryId(0L);
        categoryRepository.save(mappers.convertToCategory(categoryDto));
    }

    public void updateCategory(CategoryDto categoryDto, Long id) {
        if (id > 0) {
            Category category = categoryRepository.findById(id).orElse(null);
            if (category != null) {
                categoryDto.setCategoryId(id);
                categoryRepository.save(mappers.convertToCategory(categoryDto));
            } else {
                throw new DataNotFoundInDataBaseException("Data not found in database.");
            }
        } else {
            throw new InvalidValueExeption("The value you entered is not valid."); }
        }
}
