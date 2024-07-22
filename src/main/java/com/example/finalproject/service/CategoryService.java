package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.requestdto.CategoryRequestDto;
import com.example.finalproject.dto.responsedto.CategoryResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exception.DataAlreadyExistsException;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
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
        List<Category> categoriesList = categoryRepository.findAll();
        return MapperUtil.convertList(categoriesList, mappers::convertToCategoryResponseDto);
    }


    public void deleteCategory(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new DataNotFoundInDataBaseException("Category not found in database.");
        }
    }


    public void insertCategory(CategoryRequestDto categoryRequestDto) {
        Category checkCategory = categoryRepository.findCategoryByName(categoryRequestDto.getName());
        if(checkCategory == null){
            Category category = mappers.convertToCategory(categoryRequestDto);
            category.setCategoryId(0L);
            categoryRepository.save(category);
        } else {
            throw new DataAlreadyExistsException("The category with this name already exists.");
        }
    }


    public void updateCategory(CategoryRequestDto categoryRequestDto, Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(categoryRequestDto.getName());
            categoryRepository.save(category);
        } else {
            throw new DataNotFoundInDataBaseException("Category not found in database.");
        }
    }
}
