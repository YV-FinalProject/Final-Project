package com.example.finalproject.controller;

import com.example.finalproject.dto.CategoryRequestDto;
import com.example.finalproject.dto.CategoryResponseDto;
import com.example.finalproject.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryServiceMock;

    private CategoryResponseDto categoryResponseExpectedDto;
    private CategoryRequestDto categoryRequestExpectedDto;


    @BeforeEach
    void setUp() {
        categoryResponseExpectedDto = CategoryResponseDto.builder()
                .categoryId(1L)
                .name("Name 1")
                .build();
        categoryRequestExpectedDto = CategoryRequestDto.builder()
                .name("Name 1")
                .build();
    }

    @Test
    void getCategories() throws Exception {
        when(categoryServiceMock.getCategories()).thenReturn(List.of(categoryResponseExpectedDto));
        this.mockMvc.perform(get("/categories")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..name").value("Name 1"));
    }

    @Test
    void deleteProductsById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/categories/{id}", id)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void insertProducts() throws Exception {
        Long id=0L;
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestExpectedDto))).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateProducts() throws Exception {
        Long id =1L;
        this.mockMvc.perform(put("/categories/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestExpectedDto))).andDo(print())
                .andExpect(status().isOk());

    }
}