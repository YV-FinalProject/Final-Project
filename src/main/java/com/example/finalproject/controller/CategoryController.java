package com.example.finalproject.controller;


import com.example.finalproject.dto.requestdto.CategoryRequestDto;
import com.example.finalproject.dto.responsedto.CategoryResponseDto;
import com.example.finalproject.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    public void deleteCategoriesById(@PathVariable @Valid @Positive (message = "Invalid Id: Id must be a whole positive number") Long id) {
        categoryService.deleteCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCategories(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryService.insertCategories(categoryRequestDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategories(@RequestBody @Valid CategoryRequestDto categoryRequestDto,
                                 @PathVariable @Valid @Positive (message = "Invalid Id: Id must be a whole positive number") Long id) {
         categoryService.updateCategory(categoryRequestDto,id);
    }
}