package com.example.finalproject.controller;


import com.example.finalproject.dto.CategoryRequestDto;
import com.example.finalproject.dto.CategoryResponseDto;
import com.example.finalproject.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getCategories() {
        return categoryService.getCategories();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoriesById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCategories(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryService.insertCategories(categoryRequestDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategories(@RequestBody @Valid CategoryRequestDto categoryRequestDto, @PathVariable Long id) {
         categoryService.updateCategory(categoryRequestDto,id);
    }
}