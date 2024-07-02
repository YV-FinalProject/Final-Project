package com.example.finalproject.service;

import com.example.finalproject.dto.requestdto.CategoryRequestDto;
import com.example.finalproject.dto.responsedto.CategoryResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exception.DataAlreadyExistsException;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private CategoryService categoryServiceMock;

    private CategoryResponseDto categoryResponseDto;
    private CategoryRequestDto categoryRequestDto, wrongCategoryRequestDto;
    private Category category;

    DataNotFoundInDataBaseException dataNotFoundInDataBaseException;
    InvalidValueExeption invalidValueExeption;
    DataAlreadyExistsException dataAlreadyExistsException;

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

        wrongCategoryRequestDto = CategoryRequestDto.builder()
                .name("Wrong category")
                .build();
    }

    @Test
    void getCategories() {
        when(categoryRepositoryMock.findAll()).thenReturn(List.of(category));
        when(mappersMock.convertToCategoryResponseDto(any(Category.class))).thenReturn(categoryResponseDto);
        List<CategoryResponseDto> actualList = categoryServiceMock.getCategories();
        verify(mappersMock, times(1)).convertToCategoryResponseDto(any(Category.class));

        assertFalse(actualList.isEmpty());
        assertEquals(category.getCategoryId(), actualList.getFirst().getCategoryId());
    }

    @Test
    void deleteCategoryById() {
        Long id = 1L;
        Long wrongId = 10L;
        when(categoryRepositoryMock.findById(id)).thenReturn(Optional.of(category));
        categoryServiceMock.deleteCategoryById(id);
        verify(categoryRepositoryMock,times(1)).findById(id);
        verify(categoryRepositoryMock,times(1)).deleteById(id);

        when(categoryRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> categoryServiceMock.deleteCategoryById(wrongId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void insertCategories() {

        when(categoryRepositoryMock.findCategoryByName(categoryRequestDto.getName())).thenReturn(null);
        when(mappersMock.convertToCategory(any(CategoryRequestDto.class))).thenReturn(category);
        category.setCategoryId(0L);
        categoryServiceMock.insertCategories(categoryRequestDto);
        verify(categoryRepositoryMock, times(1)).save(any(Category.class));

        when(categoryRepositoryMock.findCategoryByName(wrongCategoryRequestDto.getName())).thenReturn(category);
        dataAlreadyExistsException = assertThrows(DataAlreadyExistsException.class,
                () -> categoryServiceMock.insertCategories(wrongCategoryRequestDto));
    }

    @Test
    void updateCategory() {
        Long id = 1L;
        Long wrongId = 10L;
        Long negativeId = -5L;
        when(categoryRepositoryMock.findById(anyLong())).thenReturn(Optional.of(category));
        category.setName(categoryRequestDto.getName());
        categoryServiceMock.updateCategory(categoryRequestDto,id);
        verify(categoryRepositoryMock, times(1)).save(category);

        when(categoryRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> categoryServiceMock.updateCategory(wrongCategoryRequestDto, wrongId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());

        invalidValueExeption = assertThrows(InvalidValueExeption.class,
                () -> categoryServiceMock.updateCategory(wrongCategoryRequestDto, negativeId));
        assertEquals("The value you entered is not valid.", invalidValueExeption.getMessage());
    }
}