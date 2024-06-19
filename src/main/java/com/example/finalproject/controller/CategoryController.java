package com.example.finalproject.controller;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.entity.CategoryEntity;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll(){
        log.debug("Получен запрос на перечисление всех категорий");
        List<CategoryDto> categoryDtoList = categoryService
                .getAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtoList);
    }
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryCreateDto categoryCreateDto){
        log.debug("Получен запрос на создание новой категории: {}", categoryCreateDto.getName());
        CategoryEntity createdCategoryEntity = categoryService.create(categoryCreateDto);
        CategoryDto categoryDto = categoryMapper.toDto(createdCategoryEntity);
        log.debug("Категория успешно создана с идентификатором: {}",createdCategoryEntity.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }
}
