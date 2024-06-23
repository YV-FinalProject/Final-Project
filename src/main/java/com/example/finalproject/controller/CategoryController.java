package com.example.finalproject.controller;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exceptions.CategoryInvalidArgumentException;
import com.example.finalproject.exceptions.CategoryNotFoundException;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;
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
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id){
        log.debug("Получен запрос на получение категории по идентификатору ID: {}", id);
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryCreateDto categoryCreateDto){
        log.debug("Получен запрос на создание новой категории: {}", categoryCreateDto.getName());
        Category createdCategory = categoryService.create(categoryCreateDto);
        CategoryDto categoryDto = categoryMapper.toDto(createdCategory);
        log.debug("Категория успешно создана с идентификатором: {}", createdCategory.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> edit(@PathVariable Long id,
                                            @Valid @RequestBody CategoryCreateDto categoryCreateDto){
        log.debug("Получен запрос на редактирование категории с идентификатором: {}",id);
        Category updateCategory = categoryService.edit(id, categoryCreateDto);
        log.debug("Категория с идентификатором: {} успешно обновлена", updateCategory.getId());
        return ResponseEntity.ok(categoryMapper.toDto(updateCategory));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.debug("Получен запрос на удаление категории с идентификатором: {}", id);
        categoryService.delete(id);
        log.debug("Категория с идентификатором: {} успешно удалена", id);
        return ResponseEntity.noContent().build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryInvalidArgumentException.class)
    public Map<String, String> handleInvalidArgumentException(CategoryInvalidArgumentException exception){
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public Map<String, String> handleNotFoundException(CategoryNotFoundException  exception){
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }
}
























