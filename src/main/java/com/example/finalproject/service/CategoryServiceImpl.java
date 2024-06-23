package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exceptions.CategoryNotFoundException;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl{
    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryMapper categoryMapper;

    public List<Category> getAll() {
        return categoryJpaRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryJpaRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Категория с " +
                "идентификатором id" + id + "не найдено."));
    }

    public Category create(CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.createDtoToEntity(categoryCreateDto);
        Category savedEntity = categoryJpaRepository.save(category);
        return savedEntity;
    }

    public Category edit(Long id, CategoryCreateDto categoryCreateDto) {
        return categoryJpaRepository.findById(id).map(category ->{
            category.setName(categoryCreateDto.getName());
            Category updateCategory = categoryJpaRepository.save(category);
            return updateCategory;
        }).orElseThrow(() -> {
            return new CategoryNotFoundException("Категория с идентификатором " + id + " не найдено.");
        });
    }


    public void delete(Long id) {
        if(!categoryJpaRepository.existsById(id)){
            throw new CategoryNotFoundException("Категория не найдена.");
        }
        categoryJpaRepository.deleteById(id);
    }
}