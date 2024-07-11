package com.example.finalproject.controller;


import com.example.finalproject.dto.requestdto.CategoryRequestDto;
import com.example.finalproject.dto.responsedto.CategoryResponseDto;
import com.example.finalproject.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Category controller", description="Описание контролера")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getCategories() {
        return categoryService.getCategories();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoriesById(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long id) {
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
                                 @PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long id) {
        categoryService.updateCategory(categoryRequestDto, id);
    }
}