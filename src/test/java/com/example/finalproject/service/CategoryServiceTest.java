package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryRequestDto;
import com.example.finalproject.dto.CategoryResponseDto;
import com.example.finalproject.dto.ProductRequestDto;
import com.example.finalproject.dto.ProductResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private ModelMapper modelMapperMock;


    @InjectMocks
    private CategoryService categoryServiceTest;

    private CategoryResponseDto categoryResponseExpectedDto;
    private CategoryRequestDto categoryRequestExpectedDto;
    private Category categoryResponseExpected, categoryRequestExpected;
    private Category categoryExpected;

    @BeforeEach
    void setUp() {
        categoryResponseExpectedDto = new CategoryResponseDto(1L,"Test category");
        categoryExpected = new Category(1L,"Test category",null);
        categoryRequestExpectedDto = new CategoryRequestDto("Test Category");
    }

    @Test
    void getCategories() {
        when(categoryRepositoryMock.findAll()).thenReturn(List.of(categoryExpected));
        when(mappersMock.convertToCategoryResponseDto(any(Category.class))).thenReturn(categoryResponseExpectedDto);
        List<CategoryResponseDto> ee = categoryServiceTest.getCategories();
        verify(mappersMock, times(1)).convertToCategoryResponseDto(any(Category.class));
    }

    @Test
    void deleteCategoryById() {
        long id = 1L;
        when(categoryRepositoryMock.findById(id)).thenReturn(Optional.of(categoryExpected));
        categoryServiceTest.deleteCategoryById(id);
        verify(categoryRepositoryMock,times(1)).findById(id);
        //verify(categoryRepositoryMock,times(1)).delete(categoryExpected);

    }

    @Test
    void insertCategories() {
        when(mappersMock.convertToCategory(any(CategoryRequestDto.class))).thenReturn(categoryExpected);
        when(categoryRepositoryMock.findById(anyLong())).thenReturn(Optional.of(categoryExpected));
        categoryServiceTest.insertCategories(categoryRequestExpectedDto);
        verify(categoryRepositoryMock, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory() {
        when(mappersMock.convertToCategory(any(CategoryRequestDto.class))).thenReturn(categoryExpected);
        when(categoryRepositoryMock.findById(anyLong())).thenReturn(Optional.of(categoryExpected));
        Long id = 1L;
        categoryServiceTest.updateCategory(categoryRequestExpectedDto,id);
      //  verify(categoryRepositoryMock, times(1)).save(any(Category.class));
    }
}