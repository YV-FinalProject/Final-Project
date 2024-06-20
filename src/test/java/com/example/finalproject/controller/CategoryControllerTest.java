package com.example.finalproject.controller;

import com.example.finalproject.dto.CategoryCreateDto;
import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.entity.CategoryEntity;
import com.example.finalproject.exceptions.CategoryNotFoundException;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @Mock
    private CategoryService categoryService;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryController categoryController;
    private MockMvc mockMvc;
    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }
    @Test
    public void getAllCategoriesTest() throws Exception{
        CategoryEntity categoryOneEntity = new CategoryEntity(1L, "Sapa", null);
        CategoryEntity categoryTwoEntity = new CategoryEntity(2L, "Kosa", null);
        List<CategoryEntity> categoryEntityList = Arrays.asList(categoryOneEntity, categoryTwoEntity);
        CategoryDto categoryOneDto = new CategoryDto(1L,"Sapa");
        CategoryDto categoryTwoDto = new CategoryDto(2L, "Kosa");
        List<CategoryDto> categoryDtoList = Arrays.asList(categoryOneDto, categoryTwoDto);
        given(categoryService.getAll()).willReturn(categoryEntityList);
        given(categoryMapper.toDto(any(CategoryEntity.class))).willReturn(categoryOneDto, categoryTwoDto);
        mockMvc.perform(get("/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Sapa")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Kosa")));
        verify(categoryService,times(1)).getAll();
    }
    @Test
    public void getCategoryByIdTest() throws Exception{
        Long id = 1L;
        CategoryEntity categoryEntity = new CategoryEntity(id, "Sapa",null);
        CategoryDto categoryDto = new CategoryDto(id, "Sapa");
        given(categoryService.getById(id)).willReturn(categoryEntity);
        given(categoryMapper.toDto(any(CategoryEntity.class))).willReturn(categoryDto);
        mockMvc.perform(get("/v1/categories/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is("Sapa")));
    }
    @Test
    void testGetCategoryByIdNotFound() throws Exception{
        Long id = 1L;
        when(categoryService.getById(id)).thenThrow(new CategoryNotFoundException("Категория не найдена."));
        mockMvc.perform(get("/v1/categories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Категория не найдена.")));
    }
    @Test
    public void createCategoryTest() throws Exception{
        CategoryEntity categoryEntity = new CategoryEntity(1L,"Sapa",null);
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("Sapa");
        CategoryDto categoryDto = new CategoryDto(1L, "Sapa");
        given(categoryService.create(any(CategoryCreateDto.class))).willReturn(categoryEntity);
        given(categoryMapper.toDto(any(CategoryEntity.class))).willReturn(categoryDto);
        mockMvc.perform(post("/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(categoryCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Sapa")));
    }
    @Test
    public void editCategoryTest() throws Exception{
        Long id = 1L;
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("Sapa2");
        CategoryEntity updatedEntity = new CategoryEntity(id, "Sapa2", null);
        CategoryDto updatedDto = new CategoryDto(id, "Sapa2");
        given(categoryService.edit(eq(id), any(CategoryCreateDto.class))).willReturn(updatedEntity);
        given(categoryMapper.toDto(any(CategoryEntity.class))).willReturn(updatedDto);
        mockMvc.perform(put("/v1/categories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(categoryCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is("Sapa2")));
    }
    @Test
    public void deleteCategoryTest() throws Exception{
        Long id = 1L;
        doNothing().when(categoryService).delete(id);
        mockMvc.perform(delete("/v1/categories/{id}", id))
                .andExpect(status().isNoContent());
    }
}

















