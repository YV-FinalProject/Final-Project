package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.ProductCountDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;

import com.fasterxml.jackson.core.io.BigDecimalParser;
import org.springframework.transaction.annotation.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Mappers mappers;
    private final MapperUtil mapperUtil;

    @Transactional
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return mappers.convertToProductResponseDto(product);

        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    @Transactional
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.deleteById(product.getProductId());
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
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
        if (category != null){
            Product productToUpdate = productRepository.findById(id).orElse(null);
            if (productToUpdate != null) {
                productToUpdate.setName(productRequestDto.getName());
                productToUpdate.setDescription(productRequestDto.getDescription());
                productToUpdate.setPrice(productRequestDto.getPrice());
                productToUpdate.setImageURL(productRequestDto.getImageURL());
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
    public void setDiscountPrice(Long productId, BigDecimal discountPrice) {
        Product productToUpdate = productRepository.findById(productId).orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setDiscountPrice(discountPrice);
            productRepository.save(productToUpdate);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    public ProductResponseDto getMaxDiscountProduct(){
        List<Product> maxDiscountProductList = productRepository.getMaxDiscountProduct();
        if(maxDiscountProductList.size() > 1){
            Random random = new Random();
            int randomNumber = random.nextInt(maxDiscountProductList.size());
             return mappers.convertToProductResponseDto(maxDiscountProductList.get(randomNumber));
        } else {
            return mappers.convertToProductResponseDto(maxDiscountProductList.getFirst());
        }
    }

    @Transactional
    public List<ProductCountDto> getTop10Products(String status) {
        List<String> temporyList = productRepository.findTop10Products(status);
        List<ProductCountDto> list1 = new ArrayList<>();
        for (String entry : temporyList) {
            String[] stringEntry = entry.split(",");
            ProductCountDto productCount = new ProductCountDto(Long.parseUnsignedLong(stringEntry[0]),
                    stringEntry[1],
                    Integer.valueOf(stringEntry[2]),
                    BigDecimalParser.parseWithFastParser(stringEntry[3]));
            list1.add(productCount);
        }
        return list1;
    }

    @Transactional
    public List<ProductResponseDto> findProductsByFilter(Long category, Double minPrice, Double maxPrice, Boolean isDiscount, String sort) {
        boolean isCategory = false;
        if (category == null) {
            isCategory = true;
        }
        if (minPrice == null) {
            minPrice = 0.00;
        }
        if (maxPrice == null) {
            maxPrice = Double.MAX_VALUE;
        }
        if (sort == null) {
            sort = "Name";
        }
        List<Product> list = productRepository.findProductsByFilter(isCategory, category, minPrice, maxPrice, !isDiscount, sort);
        return mapperUtil.convertList(list, mappers::convertToProductResponseDto);
    }
}