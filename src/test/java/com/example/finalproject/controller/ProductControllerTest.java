package com.example.finalproject.controller;


import com.example.finalproject.dto.ProductRequestDto;
import com.example.finalproject.dto.ProductResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;


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

    private ProductResponseDto productResponseDto;
    private ProductRequestDto  productRequestDto;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productResponseDto = ProductResponseDto.builder()
                .name("Name 1")
                .description("Description 1")
                .price(new BigDecimal("100.00"))
                .imageURL("http::/localhost/img/1.jpg")
                .build();
        productRequestDto = ProductRequestDto.builder()
                .name("Name 1")
                .description("Description 2")
                .price(new BigDecimal("101.00"))
                .discountPrice(new BigDecimal("101.00"))
                .imageURL("http::/localhost/img/2.jpg")
                .category("Test category")
                .build();
    }

    @Test
    void getProductsById() throws Exception {
        when(productServiceMock.getProductById(anyLong())).thenReturn(productResponseDto);
        this.mockMvc.perform(get("/products/{id}",1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value("Name 1"));
    }

    @Test
    void deleteProductsById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/products/{id}", id)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void insertProducts() throws Exception {
        Long id=0L;
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto))).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateProducts() throws Exception {
      //  when(productServiceMock.getProductById(anyLong())).thenReturn(productExpected2);
        //when(productServiceMock.updateProduct(any(ProductRequestDto.class),anyLong())).thenReturn(productExpected1);
        Long id =1L;
        this.mockMvc.perform(put("/products/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto))).andDo(print())
                .andExpect(status().isOk());

    }
}