package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryRequestDto;
import com.example.finalproject.dto.CategoryResponseDto;
import com.example.finalproject.entity.Category;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    private CategoryResponseDto categoryResponseDto;
    private CategoryRequestDto categoryRequestDto;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryResponseDto = CategoryResponseDto.builder()
                .categoryId(1L)
                .name("Test category")
                .build();

        category = new Category(
                1L,
                "Test category",
                null);

        categoryRequestDto = CategoryRequestDto.builder()
                .name("Test category")
                .build();
    }

    @Test
    void getCategories() {
        when(categoryRepositoryMock.findAll()).thenReturn(List.of(category));
        when(mappersMock.convertToCategoryResponseDto(any(Category.class))).thenReturn(categoryResponseDto);
        List<CategoryResponseDto> actualList = categoryServiceTest.getCategories();
        verify(mappersMock, times(1)).convertToCategoryResponseDto(any(Category.class));

        assertFalse(actualList.isEmpty());
        assertEquals(category.getCategoryId(), actualList.getFirst().getCategoryId());
    }

    @Test
    void deleteCategoryById() {
        Long id = 1L;
        when(categoryRepositoryMock.findById(id)).thenReturn(Optional.of(category));
        categoryServiceTest.deleteCategoryById(id);
        verify(categoryRepositoryMock,times(1)).findById(id);
        verify(categoryRepositoryMock,times(1)).deleteById(id);
    }

    @Test
    void insertCategories() {
        category.setCategoryId(0L);
        when(mappersMock.convertToCategory(any(CategoryRequestDto.class))).thenReturn(category);
        categoryServiceTest.insertCategories(categoryRequestDto);
        verify(categoryRepositoryMock, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory() {
        Long id = 1L;
        when(categoryRepositoryMock.findById(anyLong())).thenReturn(Optional.of(category));
        category.setName(categoryRequestDto.getName());
        categoryServiceTest.updateCategory(categoryRequestDto,id);
        verify(categoryRepositoryMock, times(1)).save(category);
    }
}