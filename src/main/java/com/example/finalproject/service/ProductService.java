package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.querydto.ProductCountDto;
import com.example.finalproject.dto.querydto.ProductPendingDto;
import com.example.finalproject.dto.querydto.ProductProfitDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Mappers mappers;
    private final MapperUtil mapperUtil;


    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return mappers.convertToProductResponseDto(product);

        } else {
            throw new DataNotFoundInDataBaseException("Product not found in database.");
        }
    }

    @Transactional
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.deleteById(product.getProductId());
        } else {
            throw new DataNotFoundInDataBaseException("Product not found in database.");
        }
    }

    @Transactional
    public void insertProduct(ProductRequestDto productRequestDto) {
        Category category = categoryRepository.findCategoryByName(productRequestDto.getCategory());
        if (category != null) {
            Product productToInsert = mappers.convertToProduct(productRequestDto);
            productToInsert.setProductId(0L);
            productToInsert.setCategory(category);
            productToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            productRepository.save(productToInsert);
        } else {
            throw new DataNotFoundInDataBaseException("Category not found in database.");
        }
    }

    @Transactional
    public void updateProduct(ProductRequestDto productRequestDto, Long id) {
        Category category = categoryRepository.findCategoryByName(productRequestDto.getCategory());
        if (category != null) {
            Product productToUpdate = productRepository.findById(id).orElse(null);
            if (productToUpdate != null) {
                productToUpdate.setName(productRequestDto.getName());
                productToUpdate.setDescription(productRequestDto.getDescription());
                productToUpdate.setPrice(productRequestDto.getPrice());
                productToUpdate.setImageUrl(productRequestDto.getImageUrl());
                productToUpdate.setCategory(category);
                productToUpdate.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                productRepository.save(productToUpdate);
            } else {
                throw new DataNotFoundInDataBaseException("Product not found in database.");
            }
        } else {
            throw new DataNotFoundInDataBaseException("Category not found in database.");
        }

    }

    @Transactional
    public void setDiscountPrice(Long id, BigDecimal discountPrice) {
        Product productToUpdate = productRepository.findById(id).orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setDiscountPrice(discountPrice);
            productRepository.save(productToUpdate);
        } else {
            throw new DataNotFoundInDataBaseException("Product not found in database.");
        }
    }

    public ProductResponseDto getMaxDiscountProduct() {
        List<Product> maxDiscountProductList = productRepository.getMaxDiscountProduct();
        if (maxDiscountProductList.size() > 1) {
            Random random = new Random();
            int randomNumber = random.nextInt(maxDiscountProductList.size());
            return mappers.convertToProductResponseDto(maxDiscountProductList.get(randomNumber));
        } else {
            return mappers.convertToProductResponseDto(maxDiscountProductList.getFirst());
        }
    }
    public List<ProductCountDto> getTop10Products(String status) {

        return mapperUtil.convertList(productRepository.findTop10Products(status),mappers::convertToProductCountDto);
    }

    @Transactional
    public List<ProductResponseDto> findProductsByFilter(Long category, BigDecimal minPrice, BigDecimal maxPrice, Boolean hasDiscount, String[] sort) {
        boolean ascending = true;
        Sort sortObject = orderBy("name", true);// по умолчанию
        boolean hasCategory = false;

        if (category == null) { hasCategory = true; }
        if (minPrice == null) { minPrice =BigDecimal.valueOf( 0.00); }
        if (maxPrice == null) { maxPrice =BigDecimal.valueOf( Double.MAX_VALUE); }
        if (sort != null) {
            if (sort[1].equals("desc")) {
                ascending = false;
            }
            sortObject = orderBy(sort[0], ascending);
        }
        return mapperUtil.convertList(productRepository.findProductsByFilter(hasCategory, category, minPrice, maxPrice, hasDiscount, sortObject), mappers::convertToProductResponseDto);
    }


    public List<ProductPendingDto> findProductPending(Integer day) {
        return mapperUtil.convertList(productRepository.findProductPending(day),mappers::convertToProductPendingDto);
    }


    public List<ProductProfitDto> findProductProfit(String period, Integer value) {
        return mapperUtil.convertList(productRepository.findProfitByPeriod(period, value),mappers::convertToProductProfitDto);
    }


    private Sort orderBy(String sort, Boolean ascending) {
        if (!ascending) {
            return Sort.by(Sort.Direction.DESC, sort);
        } else {
            return Sort.by(Sort.Direction.ASC, sort);
        }
    }

}