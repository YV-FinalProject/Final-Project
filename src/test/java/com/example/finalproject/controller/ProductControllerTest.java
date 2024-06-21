package com.example.finalproject.controller;


import com.example.finalproject.controller.ProductController;
import com.example.finalproject.dto.ProductDto;
import com.example.finalproject.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productServiceMock;

    private ProductDto productExpected1, productExpected2;
    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productExpected1 = ProductDto.builder()
                .name("Name 1")
                .description("Description 1")
                .price(new BigDecimal("100.00"))
                .imageURL("http::/localhost/img/1.jpg")
                .CategoryId(1L)
                .build();
        productExpected2 = ProductDto.builder()
                .name("Name 1")
                .description("Description 2")
                .price(new BigDecimal("101.00"))
                .imageURL("http::/localhost/img/2.jpg")
                .CategoryId(1L)
                .build();
    }

    @Test
    void getProductsById() throws Exception {
        when(productServiceMock.getProductById(anyLong())).thenReturn(productExpected1);
        this.mockMvc.perform(get("/products/{id}",1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value("Name 1"));
    }

    @Test
    void deleteProductsById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/products/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productsId").doesNotExist());
    }

    @Test
    void insertProducts() throws Exception {
        when(productServiceMock.insertProduct(any(ProductDto.class))).thenReturn(productExpected1);
        this.mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productExpected1))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value("Name 1"));
    }

    @Test
    void updateProducts() throws Exception {
        when(productServiceMock.getProductById(anyLong())).thenReturn(productExpected1);
        when(productServiceMock.updateProduct(any(ProductDto.class),anyLong())).thenReturn(productExpected1);
        Long id =1L;
        this.mockMvc.perform(put("/products/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productExpected2))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value("Name 1"));
    }
}