package com.example.finalproject.controller;

import com.example.finalproject.dto.responsedto.CategoryResponseDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    void setUp() {

        productResponseDto = ProductResponseDto.builder()
                .productId(1L)
                .name("Name")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .discountPrice(new BigDecimal("100.00"))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .imageURL("https://example.com/images/deroma_white_garden_pot.jpg")
                .categoryResponseDto(CategoryResponseDto.builder()
                        .categoryId(1L)
                        .name("Test category")
                        .build())
                .build();
        productRequestDto = ProductRequestDto.builder()
                .name("Name")
                .description("Description")
                .price(new BigDecimal("101.00"))
                .discountPrice(new BigDecimal("101.00"))
                .imageURL("https://example.com/images/magic_garden_seeds.jpg")
                .category("Test category")
                .build();
    }

    @Test
    void getProductsById() throws Exception {
        Long id = 1L;
        when(productServiceMock.getProductById(anyLong())).thenReturn(productResponseDto);
        this.mockMvc.perform(get("/products/{id}",id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    void deleteProductsById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/products/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").doesNotExist())
                .andExpect(jsonPath("$.name").doesNotExist());
    }

    @Test
    void insertProducts() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto))).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateProducts() throws Exception {
        Long id =1L;
        this.mockMvc.perform(put("/products/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto))).andDo(print())
                .andExpect(status().isOk());
    }
}