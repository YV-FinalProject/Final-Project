package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exception.CategoryWrongValueException;
import com.example.finalproject.exceptions.CategoryNotFoundException;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CartRepository;
import com.example.finalproject.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {
    private final CategoryJpaRepository categoryJpaRepository;
    private final CartRepository cartRepository;

    private final Mappers mappers;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getCategory() {
        return MapperUtil.convertList(categoryJpaRepository.findAll(), mappers::convertToCategoryDto);
    }

    public CategoryDto getCategoryById(Long id) {
        if (!categoryJpaRepository.findById(id).isPresent()) {
            throw new CategoryNotFoundException("the given category was not found");
        }
        return mappers.convertToCategoryDto(categoryJpaRepository.findById(id).orElse(null));
    }

    public CategoryDto editCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() > 0
                && categoryJpaRepository.findById(categoryDto.getId()).orElse(null) != null) {
            return mappers.convertToCategoryDto(categoryJpaRepository.save(mappers.convertToCategory(categoryDto)));
        }
        throw new CategoryWrongValueException("failed to update category's data");
    }


    public void deleteCategoryById(Long id) {
        if (!categoryJpaRepository.findById(id).isPresent()) {
            throw new CategoryNotFoundException("failed to delete category as it was not found");
        }
        categoryJpaRepository.findById(id).ifPresent(categoryJpaRepository::delete);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryDto.getName() != null) {
            return mappers.convertToCategoryDto(categoryJpaRepository.save(mappers.convertToCategory(categoryDto)));
        }
        throw new CategoryWrongValueException("failed to create category due to the wrong parameters");
    }
}


































