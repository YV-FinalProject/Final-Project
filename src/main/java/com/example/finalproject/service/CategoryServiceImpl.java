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

    @Override
    public CategoryEntity edit(Long id, CategoryCreateDto categoryCreateDto) {
        log.debug("Редактирование категории с идентификатором: {}", id);
        return categoryJpaRepository.findById(id).map(category ->{
            category.setName(categoryCreateDto.getName());
            CategoryEntity updateCategory = categoryJpaRepository.save(category);
            log.debug("Категория с идентификатором: {} успешно обновлена" , id);
            return updateCategory;
        }).orElseThrow(() -> {
            log.error("Категория с идентификатором {} не найдена для обновления", id);
            return new CategoryNotFoundException("Категория с идентификатором " + id + " не найдено.");
        });
    }

    @Override
    public void delete(Long id) {
        log.debug("Удаление категории с идентификатором: {}", id);
        if(!categoryJpaRepository.existsById(id)){
            log.error("Категория с идентификатором {} не найдена для удаления", id);
            throw new CategoryNotFoundException("Категория не найдена.");
        }
        categoryJpaRepository.deleteById(id);
        log.debug("Категория с идентификатором: {} успешно удалена.", id);
    }
}