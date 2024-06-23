package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exceptions.CategoryNotFoundException;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.repository.CategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryJpaRepository categoryJpaRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;
    @Test
    void getAllCategories(){
        when(categoryJpaRepository.findAll()).thenReturn(Arrays.asList(new Category(),new Category()));
        List<Category> result = categoryServiceImpl.getCategory();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryJpaRepository).findAll();
    }
    @Test
    void getCategoryById_WhenCategoryExists(){
        Long id = 1L;
        Category category = new Category();
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.of(category));
        Category result = categoryServiceImpl.getCategoryById(id);
        assertNotNull(result);
        verify(categoryJpaRepository).findById(id);
    }
    @Test
    void getCategoryById_WhenCategoryDoesNotExist(){
        Long id = 1L;
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.getCategoryById(id));
        verify(categoryJpaRepository).findById(id);
    }
    @Test
    void createCategory(){
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        categoryCreateDto.setName("Test Category");
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        when(categoryMapper.createDtoToEntity(any(CategoryCreateDto.class))).thenReturn(category);
        when(categoryJpaRepository.save(any(Category.class))).thenReturn(category);
        Category result = categoryServiceImpl.createCategory(categoryCreateDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Category", result.getName());
        verify(categoryMapper).createDtoToEntity(any(CategoryCreateDto.class));
        verify(categoryJpaRepository).save(any(Category.class));
    }
    @Test
    void editCategory_WhenCategoryExists(){
        Long id = 1L;
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        categoryCreateDto.setName("Updated Name");
        Category category = new Category();
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryJpaRepository.save(any(Category.class))).thenReturn(category);
        Category result = categoryServiceImpl.editCategory(id, categoryCreateDto);
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(categoryJpaRepository).save(category);
        verify(categoryJpaRepository).findById(id);
    }
    @Test
    void editCategory_WhenCategoryDoesNotExist(){
        Long id = 1L;
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.editCategory(id,
                new CategoryCreateDto()));
    }
    @Test
    void deleteCategory_WhenCategoryExist(){
        Long id = 1L;
        when(categoryJpaRepository.existsById(id)).thenReturn(true);
        categoryServiceImpl.deleteCategoryById(id);
        verify(categoryJpaRepository).deleteById(id);
    }
    @Test
    void deleteCategory_WhenCategoryDoesNotExist(){
        Long id = 1L;
        when(categoryJpaRepository.existsById(id)).thenReturn(false);
        assertThrows(CategoryNotFoundException.class, ()  -> categoryServiceImpl.deleteCategoryById(id));
        verify(categoryJpaRepository, never()).deleteById(id);
    }
}
