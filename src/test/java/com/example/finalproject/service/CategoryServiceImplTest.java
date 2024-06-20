package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.entity.CategoryEntity;
import com.example.finalproject.exceptions.CategoryInvalidArgumentException;
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
        when(categoryJpaRepository.findAll()).thenReturn(Arrays.asList(new CategoryEntity(),new CategoryEntity()));
        List<CategoryEntity> result = categoryServiceImpl.getAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryJpaRepository).findAll();
    }
    @Test
    void getCategoryById_WhenCategoryExists(){
        Long id = 1L;
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.of(categoryEntity));
        CategoryEntity result = categoryServiceImpl.getById(id);
        assertNotNull(result);
        verify(categoryJpaRepository).findById(id);
    }
    @Test
    void getCategoryById_WhenCategoryDoesNotExist(){
        Long id = 1L;
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.getById(id));
        verify(categoryJpaRepository).findById(id);
    }
    @Test
    void createCategory(){
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        categoryCreateDto.setName("Test Category");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Test Category");
        when(categoryMapper.createDtoToEntity(any(CategoryCreateDto.class))).thenReturn(categoryEntity);
        when(categoryJpaRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
        CategoryEntity result = categoryServiceImpl.create(categoryCreateDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Category", result.getName());
        verify(categoryMapper).createDtoToEntity(any(CategoryCreateDto.class));
        verify(categoryJpaRepository).save(any(CategoryEntity.class));
    }
    @Test
    void editCategory_WhenCategoryExists(){
        Long id = 1L;
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        categoryCreateDto.setName("Updated Name");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.of(categoryEntity));
        when(categoryJpaRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
        CategoryEntity result = categoryServiceImpl.edit(id, categoryCreateDto);
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(categoryJpaRepository).save(categoryEntity);
        verify(categoryJpaRepository).findById(id);
    }
    @Test
    void editCategory_WhenCategoryDoesNotExist(){
        Long id = 1L;
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.edit(id, new CategoryCreateDto()));
    }
    @Test
    void deleteCategory_WhenCategoryExist(){
        Long id = 1L;
        when(categoryJpaRepository.existsById(id)).thenReturn(true);
        categoryServiceImpl.delete(id);
        verify(categoryJpaRepository).deleteById(id);
    }
    @Test
    void deleteCategory_WhenCategoryDoesNotExist(){
        Long id = 1L;
        when(categoryJpaRepository.existsById(id)).thenReturn(false);
        assertThrows(CategoryNotFoundException.class, ()  -> categoryServiceImpl.delete(id));
        verify(categoryJpaRepository, never()).deleteById(id);
    }
}
