package com.example.finalproject.service;

import com.example.finalproject.dto.responsedto.CategoryResponseDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private CategoryRepository categoryRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private ProductService productServiceMock;

    DataNotFoundInDataBaseException dataNotFoundInDataBaseException;

    private ProductResponseDto productResponseDto;
    private ProductRequestDto productRequestDto, wrongProductRequestDto;
    private Product product, productToInsert;
    private Category category;

    @BeforeEach
    void setUp() {

       productResponseDto = ProductResponseDto.builder()
                .productId(1L)
                .name("Name")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .imageURL("http://localhost/img/1.jpg")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoryResponseDto(new CategoryResponseDto(1L,"Category"))
                .build();

        product = new Product(1L,
                "Name",
                "Description",
                new BigDecimal("100.00"),
                new BigDecimal("0.00"),
                "http://localhost/img/1.jpg",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                new Category(1L,"Category",null));

        productToInsert = new Product(null,
                "Name",
                "Description",
                new BigDecimal("100.00"),
                new BigDecimal("0.00"),
                "http://localhost/img/1.jpg",
                null,
                null,
                new Category(1L,"Category",null));

        category = new Category(1L,
                "Category",
                null);

        productRequestDto = ProductRequestDto.builder()
                .name("Name")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .discountPrice(new BigDecimal("0.00"))
                .imageURL("http://localhost/img/1.jpg")
                .category("Category")
                .build();

        wrongProductRequestDto = ProductRequestDto.builder()
                .name("Name")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .discountPrice(new BigDecimal("0.00"))
                .imageURL("http://localhost/img/1.jpg")
                .category("WrongCategory")
                .build();
    }

    @Test
    void getProductById() {
        Long id = 1L;
        Long wrongId = 58L;

        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(product));
        when(mappersMock.convertToProductResponseDto(any(Product.class))).thenReturn(productResponseDto);
        ProductResponseDto actualProductResponseDto = productServiceMock.getProductById(id);

        verify(mappersMock, times(1)).convertToProductResponseDto(any(Product.class));
        verify(productRepositoryMock, times(1)).findById(id);
        assertEquals(productResponseDto.getProductId(), actualProductResponseDto.getProductId());

//        when(productRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
//        productServiceMock.getProductById(wrongId);
//        assertThrows(DataNotFoundInDataBaseException.class,
//                () -> productServiceMock.getProductById(wrongId));
//        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void deleteProductById() {
        Long id = 1L;
        Long wrongId = 35L;

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(product));
        productServiceMock.deleteProductById(id);
        verify(productRepositoryMock,times(1)).delete(product);

//        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
//        productServiceMock.getProductById(wrongId);
//        assertThrows(DataNotFoundInDataBaseException.class,
//                () -> productServiceMock.getProductById(wrongId));
//        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void insertProduct() {
        when(categoryRepositoryMock.findCategoryByName(productRequestDto.getCategory())).thenReturn(category);
        when(mappersMock.convertToProduct(any(ProductRequestDto.class))).thenReturn(productToInsert);
        productToInsert.setProductId(0L);
        productToInsert.setCategory(category);
        productToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        productServiceMock.insertProduct(productRequestDto);
        verify(mappersMock, times(1)).convertToProduct(any(ProductRequestDto.class));
        verify(productRepositoryMock, times(1)).save(productToInsert);

//        when(categoryRepositoryMock.findCategoryByName(wrongProductRequestDto.getCategory()).thenReturn(null);
//        productServiceMock.insertProduct(wrongProductRequestDto);
//        assertThrows(DataNotFoundInDataBaseException.class,
//                () -> productServiceMock.getProductById(wrongId));
//        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void updateProduct() {
        Long id = 1L;
        Long wrongId = 35L;

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(product));
        when(categoryRepositoryMock.findCategoryByName(anyString())).thenReturn(category);

        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setDiscountPrice(productRequestDto.getDiscountPrice());
        product.setImageURL(productRequestDto.getImageURL());
        product.setCategory(category);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        productServiceMock.updateProduct(productRequestDto,id);

        verify(productRepositoryMock, times(1)).save(any(Product.class));

//        when(productRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
//        when(categoryRepositoryMock.findCategoryByName(wrongProductRequestDto.getCategory()).thenReturn(null);
//        productServiceMock.insertProduct(wrongProductRequestDto, wrongId);
//        assertThrows(DataNotFoundInDataBaseException.class,
//                () -> productServiceMock.getProductById(wrongId));
//        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }
}