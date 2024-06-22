package com.example.finalproject.service;

import com.example.finalproject.dto.ProductDto;
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
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
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

    private ProductDto productExpectedDto;
    private Product productExpected;


    @BeforeEach
    void setUp() {
        productExpectedDto = ProductDto.builder()
                .name("Name 1")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .image("http::/localhost/img/1.jpg")
                .CategoryId(1L)
                .build();
        productExpected = new Product(1L,
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
    void getProduct()  {
//        when(productRepositoryMock.findAll()).thenReturn(List.of(productExpected));
//        when(mappersMock.convertToProductDto(any(Product.class))).thenReturn(productExpectedDto);
//        assertEquals(List.of(productExpectedDto),productServiceTest.getProduct());
    }

    @Test
    void getProductById() {
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(productExpected));
        when(mappersMock.convertToProductDto(any(Product.class))).thenReturn(productExpectedDto);
        assertEquals(productServiceTest.getProductById(productExpected.getProductId()),productExpectedDto);
        verify(mappersMock, times(1)).convertToProductDto(any(Product.class));

    }

    @Test
    void deleteProductById() {
        long id = 1L;
        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(productExpected));
        productServiceTest.deleteProductById(id);
        verify(productRepositoryMock,times(1)).delete(productExpected);
    }

    @Test
    void insertProduct() {

        when(mappersMock.convertToProduct(productExpectedDto)).thenReturn(productExpected);
        when(productRepositoryMock.save(any(Product.class))).thenReturn(productExpected);
        when(mappersMock.convertToProductDto(productExpected)).thenReturn(productExpectedDto);

        ProductDto actualProductDto = productServiceTest.insertProduct(productExpectedDto);
        assertNotNull(actualProductDto);
        verify(productRepositoryMock, times(1)).save(any(Product.class));
        verify(mappersMock, times(1)).convertToProductDto(any(Product.class));
    }

    @Test
    void updateProduct() {
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(productExpected));
        when(productRepositoryMock.save(any(Product.class))).thenReturn(productExpected);
        when(mappersMock.convertToProductDto(productExpected)).thenReturn(productExpectedDto);
        Long id = 1L;
        ProductDto actualProductDto = productServiceTest.updateProduct(productExpectedDto,id);
        assertNotNull(actualProductDto);

        verify(productRepositoryMock, times(1)).save(any(Product.class));
        verify(mappersMock, times(1)).convertToProductDto(any(Product.class));

    }
}