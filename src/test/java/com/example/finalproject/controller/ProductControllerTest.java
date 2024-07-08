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
import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
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
                .andExpect(status().isOk());

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
        mockMvc.perform(put("/products/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void setDiscountPrice() throws Exception {
        mockMvc.perform(put("/products?id=1&discountPrice=2.55"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getMaxDiscountProduct() throws Exception {
        mockMvc.perform(get("/products/maxDiscount"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void getProducts() throws Exception {
        Boolean hasCategory = true;
        Long categoryId = 1L;
        BigDecimal minPrice = BigDecimal.valueOf(0.00);
        BigDecimal maxPrice = BigDecimal.valueOf(100.00);
        Boolean hasDiscount = true;
        String[] strSort = new String[]{"Name","asc"};

        when(productServiceMock.findProductsByFilter(categoryId,minPrice,maxPrice,hasDiscount,strSort)).thenReturn(
                (List.of(productResponseDto)));
        this.mockMvc.perform(get("/products",categoryId,minPrice,maxPrice,hasDiscount,strSort)).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getTop10Products(String s) throws Exception {
        String Status = "PAID";
        when(productServiceMock.getTop10Products(anyString())).thenReturn(null);
        this.mockMvc.perform(get("/products/top10","Paid")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProductPending() throws Exception {
        Integer days = 55;
        when(productServiceMock.findProductPending(anyInt())).thenReturn(null);
        this.mockMvc.perform(get("/products/pending",days)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProffitByPeriod() throws Exception {
        String type = "WEEK";
        Integer period = 55;
        when(productServiceMock.findProductProfit(anyString(),anyInt())).thenReturn(null);
        this.mockMvc.perform(get("/products/profit",type,period)).andDo(print())
                .andExpect(status().isOk());
    }

}