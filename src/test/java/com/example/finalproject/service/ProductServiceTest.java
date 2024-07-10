package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.querydto.ProductCountDto;
import com.example.finalproject.dto.responsedto.CategoryResponseDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.entity.query.ProductCountInterface;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private CategoryRepository categoryRepositoryMock;

    @Mock
    private Mappers mappersMock;

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
                new Category(1L,"Category",null),
                null,
                null,
                null);

        productToInsert = new Product(null,
                "Name",
                "Description",
                new BigDecimal("100.00"),
                new BigDecimal("0.00"),
                "http://localhost/img/1.jpg",
                null,
                null,
                new Category(1L,"Category",null),
                null,
                null,
                null);

        category = new Category(1L,
                "Category",
                null);

        productRequestDto = ProductRequestDto.builder()
                .name("Name")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .imageURL("http://localhost/img/1.jpg")
                .category("Category")
                .build();

        wrongProductRequestDto = ProductRequestDto.builder()
                .name("Name")
                .description("Description")
                .price(new BigDecimal("100.00"))
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

        when(productRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> productServiceMock.getProductById(wrongId));
        assertEquals("Product not found in database.", dataNotFoundInDataBaseException.getMessage());

    }

    @Test
    void deleteProductById() {
        Long id = 1L;
        Long wrongId = 35L;

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(product));

        productServiceMock.deleteProductById(id);

        verify(productRepositoryMock,times(1)).deleteById(product.getProductId());

        when(productRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> productServiceMock.deleteProductById(wrongId));
        assertEquals("Product not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void insertProduct() {
        when(categoryRepositoryMock.findCategoryByName(productRequestDto.getCategory())).thenReturn(category);
        when(mappersMock.convertToProduct(any(ProductRequestDto.class))).thenReturn(productToInsert);

        productServiceMock.insertProduct(productRequestDto);

        verify(mappersMock, times(1)).convertToProduct(any(ProductRequestDto.class));
        verify(productRepositoryMock, times(1)).save(productToInsert);

        when(categoryRepositoryMock.findCategoryByName(wrongProductRequestDto.getCategory())).thenReturn(null);
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> productServiceMock.insertProduct(wrongProductRequestDto));
        assertEquals("Category not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void updateProduct() {
        Long id = 1L;
        Long wrongId = 58L;

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(product));
        when(categoryRepositoryMock.findCategoryByName(anyString())).thenReturn(category);

        productServiceMock.updateProduct(productRequestDto,id);

        verify(productRepositoryMock, times(1)).save(any(Product.class));

        when(productRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
        when(categoryRepositoryMock.findCategoryByName(wrongProductRequestDto.getCategory())).thenReturn(null);
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> productServiceMock.updateProduct(productRequestDto, wrongId));
        assertEquals("Product not found in database.", dataNotFoundInDataBaseException.getMessage());

        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> productServiceMock.updateProduct(wrongProductRequestDto, id));
        assertEquals("Category not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void setDiscountPrice(){
        Long id = 1L;
        Long wrongId = 58L;
        BigDecimal discountPrice = new BigDecimal(2.55);

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(product));
        product.setDiscountPrice(discountPrice);

        productServiceMock.setDiscountPrice(id, discountPrice);

        verify(productRepositoryMock, times(1)).save(any(Product.class));


        when(productRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> productServiceMock.setDiscountPrice(wrongId, discountPrice));
        assertEquals("Product not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void getMaxDiscountProduct(){
        List<Product> maxDiscountProductList = List.of(product);
        when(productRepositoryMock.getMaxDiscountProduct()).thenReturn(maxDiscountProductList);
        when(mappersMock.convertToProductResponseDto(any(Product.class))).thenReturn(productResponseDto);

        productServiceMock.getMaxDiscountProduct();

        verify(productRepositoryMock, times(1)).getMaxDiscountProduct();
        verify(mappersMock, times(1)).convertToProductResponseDto(any(Product.class));
    }


//    @Test
//    void getTop10Products() {
//        ProductCountDto productCountDtoTest = new ProductCountDto(1L, "sss", 3, BigDecimal.valueOf(22.0));
//
//
//        String sort = "Price";
//        when(productRepositoryMock.findTop10Products(anyString())).thenReturn(MapperUtil.convertList(List.of(productCountDtoTest)),mappersMock::convertToProductCountDto);
//        when(mappersMock.convertToProductResponseDto(any(Product.class))).thenReturn(productResponseDto);
//        List <ProductCountDto> actualProductResponseDto = productServiceMock.getTop10Products(sort);
//
//        verify(mappersMock, times(1)).convertToProductResponseDto(any(Product.class));
//        verify(productRepositoryMock, times(1)).findTop10Products(sort);
//       // assertEquals(productResponseDto.getProductId(), actualProductResponseDto.getProductId());
//
//    }

    @Test
    void findProductsByFilter() {
        Boolean hasCategory = true;
        Long categoryId = 1L;
        BigDecimal minPrice = BigDecimal.valueOf(0.00);
        BigDecimal maxPrice = BigDecimal.valueOf(100.00);
        Boolean hasDiscount = true;
        Sort sortObject = orderBy("name", true);
        when(productRepositoryMock.findProductsByFilter(hasCategory,categoryId,minPrice,maxPrice,hasDiscount,sortObject)).thenReturn(List.of(product));
        List<Product> actualProductResponseDto = productRepositoryMock.findProductsByFilter(hasCategory,categoryId,minPrice,maxPrice,hasDiscount,sortObject);
        assertTrue(actualProductResponseDto.size()>0);
        verify(productRepositoryMock, times(1)).findProductsByFilter(hasCategory,categoryId,minPrice,maxPrice,hasDiscount,sortObject);
        assertEquals(product.getProductId(),actualProductResponseDto.get(0).getProductId());


    }

    private Sort orderBy(String sort, Boolean ascending) {
        if (!ascending) {
            return Sort.by(Sort.Direction.DESC, sort);
        } else {
            return Sort.by(Sort.Direction.ASC, sort);
        }
    }

}

