package com.example.finalproject.service;

import com.example.finalproject.dto.ProductRequestDto;
import com.example.finalproject.dto.ProductResponseDto;
import com.example.finalproject.entity.Product;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {



    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private ProductService productServiceTest;

    private ProductResponseDto productResponseExpectedDto;
    private ProductRequestDto productRequestExpectedDto;
    private Product productResponseExpected, productRequestExpected;


    @BeforeEach
    void setUp() {
        productResponseExpectedDto = ProductResponseDto.builder()
                .productId(1L)
                .name("Name 1")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .imageURL("http::/localhost/img/1.jpg")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .CategoryId(1L)
                .build();
        productResponseExpected = new Product(1L,
                "Name 1",
                "Description 1",
                new BigDecimal("100.00"),
                "http::/localhost/img/1.jpg",
                new BigDecimal("0.00"),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                1L );

        productRequestExpectedDto = ProductRequestDto.builder()
                .name("Name 1")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .imageURL("http::/localhost/img/1.jpg")
                .CategoryId(1L)
                .build();
        productRequestExpected = new Product(1L,
                "Name 1",
                "Description 1",
                new BigDecimal("100.00"),
                "http::/localhost/img/1.jpg",
                new BigDecimal("0.00"),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                1L );

    }

    @Test
    void getProductById() {
//        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(productResponseExpected));
//        when(mappersMock.convertToProductResponseDto(any(Product.class))).thenReturn(productResponseExpectedDto);
//        ProductResponseDto ee = productServiceTest.getProductById(productResponseExpected.getProductId());
////        assertEquals(ee,productResponseExpectedDto);
//        verify(mappersMock, times(1)).convertToProductResponseDto(any(Product.class));

    }

    @Test
    void deleteProductById() {
        long id = 1L;
        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(productResponseExpected));
        productServiceTest.deleteProductById(id);
        verify(productRepositoryMock,times(1)).delete(productResponseExpected);
    }

    @Test
    void insertProduct() {

//        when(mappersMock.convertToProduct(productExpectedDto)).thenReturn(productExpected);
//        when(productRepositoryMock.save(any(Product.class))).thenReturn(productExpected);
//        when(mappersMock.convertToProductDto(productExpected)).thenReturn(productExpectedDto);
//
//        ProductRequestDto actualProductRequestDto = productServiceTest.insertProduct(productExpectedDto);
//        assertNotNull(actualProductRequestDto);
//        verify(productRepositoryMock, times(1)).save(any(Product.class));
//        verify(mappersMock, times(1)).convertToProductDto(any(Product.class));
    }

    @Test
    void updateProduct() {
//        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(productExpected));
//        when(productRepositoryMock.save(any(Product.class))).thenReturn(productExpected);
//        when(mappersMock.convertToProductDto(productExpected)).thenReturn(productExpectedDto);
//        Long id = 1L;
//        ProductRequestDto actualProductRequestDto = productServiceTest.updateProduct(productExpectedDto,id);
//        assertNotNull(actualProductRequestDto);
//
//        verify(productRepositoryMock, times(1)).save(any(Product.class));
//        verify(mappersMock, times(1)).convertToProductDto(any(Product.class));

    }
}