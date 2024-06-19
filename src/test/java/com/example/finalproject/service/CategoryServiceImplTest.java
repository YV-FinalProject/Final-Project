package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.entity.CategoryEntity;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.repository.CategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
