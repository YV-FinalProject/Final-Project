package com.example.finalproject.controller;

import com.example.finalproject.dto.querydto.ProductCountDto;
import com.example.finalproject.dto.querydto.ProductPendingDto;
import com.example.finalproject.dto.querydto.ProductProfitDto;
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
                .imageUrl("https://example.com/images/deroma_white_garden_pot.jpg")
                .categoryResponseDto(CategoryResponseDto.builder()
                        .categoryId(1L)
                        .name("Test category")
                        .build())
                .build();
        productRequestDto = ProductRequestDto.builder()
                .name("Name")
                .description("Description")
                .price(new BigDecimal("101.00"))
                .imageUrl("https://example.com/images/magic_garden_seeds.jpg")
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
        Long categoryId = 1L;
        BigDecimal minPrice = BigDecimal.valueOf(0.00);
        BigDecimal maxPrice = BigDecimal.valueOf(100.00);
        Boolean hasDiscount = true;
        String strSort = "name,asc";

        when(productServiceMock.findProductsByFilter(categoryId,minPrice,maxPrice,hasDiscount,strSort)).thenReturn(
                (List.of(productResponseDto)));
        this.mockMvc.perform(get("/products?category=1&minPrice=0.00&maxPrice=100.00&discount=true&sort=name,asc")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getTop10Products() throws Exception {
        String status = "PAID";
        ProductCountDto productCountDto = ProductCountDto.builder().productId(1L).name("Test name").count(2).sum(BigDecimal.valueOf(1.0)).build();
        when(productServiceMock.getTop10Products(status)).thenReturn(List.of(productCountDto));
        this.mockMvc.perform(get("/products/top10?status=PAID")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProductPending() throws Exception {
        Integer day = 55;
        ProductPendingDto productPendingDto = ProductPendingDto.builder().productId(1L).name("Test name").count(23).build();
        when(productServiceMock.findProductPending(day)).thenReturn(List.of(productPendingDto));
        this.mockMvc.perform(get("/products/pending?day=55")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProfitByPeriod() throws Exception {
        String period = "WEEK";
        Integer value = 55;
        ProductProfitDto productProfitDto = ProductProfitDto.builder().period(period).sum(BigDecimal.valueOf(234.33)).build();
        when(productServiceMock.findProductProfit(period,value)).thenReturn(List.of(productProfitDto));
        this.mockMvc.perform(get("/products/profit?period=WEEK&value=55")).andDo(print())
                .andExpect(status().isOk());
    }

}