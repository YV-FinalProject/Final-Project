package com.example.finalproject.controller;

import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.exception.CategoryWrongValueException;
import com.example.finalproject.exceptions.CategoryInvalidArgumentException;
import com.example.finalproject.exceptions.CategoryNotFoundException;
import com.example.finalproject.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategory(){
        return categoryService.getCategory();
    }
    @GetMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.createCategory(categoryDto);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto editCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.editCategory(categoryDto);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> errorMessage(CategoryNotFoundException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(CategoryWrongValueException.class)
    public ResponseEntity<ErrorMessage> errorMessage(CategoryWrongValueException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }
}
