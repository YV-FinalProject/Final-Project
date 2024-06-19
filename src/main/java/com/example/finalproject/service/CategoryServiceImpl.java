package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.entity.CategoryEntity;
import com.example.finalproject.exceptions.CategoryNotFoundException;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryEntity> getAll() {
        log.debug("Извлечение всех категорий");
        return categoryJpaRepository.findAll();
    }

    @Override
    public CategoryEntity getById(Long id) {
        log.debug("Получение категории по идентификатору id: {}", id);
        return categoryJpaRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Категория с " +
                "идентификатором id" + id + "не найдено."));
    }

    @Override
    public CategoryEntity create(CategoryCreateDto categoryCreateDto) {
        log.debug("Создание категории с названием: {}", categoryCreateDto.getName());
        CategoryEntity categoryEntity = categoryMapper.createDtoToEntity(categoryCreateDto);
        CategoryEntity savedEntity = categoryJpaRepository.save(categoryEntity);
        log.debug("Категория успешно создана с идентификатором: {}");
        return savedEntity;
    }
}
