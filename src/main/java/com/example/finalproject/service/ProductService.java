package com.example.finalproject.service;


import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.entity.query.ProductCount;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;
//import com.example.finalproject.repository.customs.ProductCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
 //   private final ProductCustomRepository productCustomRepository;
    private final CategoryRepository categoryRepository;
    private final Mappers mappers;
    private final MapperUtil mapperUtil;

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return mappers.convertToProductResponseDto(product);

        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    public void deleteProductById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.findById(id).ifPresent(productRepository::delete);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    public void insertProduct(ProductRequestDto productRequestDto) {
        Category category = categoryRepository.findCategoryByName(productRequestDto.getCategory());
        if (category != null) {
            Product productToInsert = mappers.convertToProduct(productRequestDto);
            productToInsert.setProductId(0L);
            productToInsert.setCategory(category);
            productToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            productRepository.save(productToInsert);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    public void updateProduct(ProductRequestDto productRequestDto, Long id) {
        if (id > 0) {
            Product productToUpdate = productRepository.findById(id).orElse(null);
            Category category = categoryRepository.findCategoryByName(productRequestDto.getCategory());
            if (productToUpdate != null && category != null) {
                productToUpdate.setName(productRequestDto.getName());
                productToUpdate.setDescription(productRequestDto.getDescription());
                productToUpdate.setPrice(productRequestDto.getPrice());
                productToUpdate.setDiscountPrice(productRequestDto.getDiscountPrice());
                productToUpdate.setImageURL(productRequestDto.getImageURL());
                productToUpdate.setCategory(category);
                productToUpdate.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                productRepository.save(productToUpdate);
            } else {
                throw new DataNotFoundInDataBaseException("Data not found in database.");
            }
        } else {
            throw new InvalidValueExeption("The value you entered is not valid.");
        }
    }

    public List<ProductCount> getTop10Products(String status) {
        List<ProductCount> list = (List<ProductCount>)(List<?>)productRepository.findTop10Products(status);
        return list;
    }

    public List findProductsByFilter(Long category, Double minPrice, Double maxPrice, Boolean isDiscount, String sort) {
        boolean isCategory = false;
        if (category == null) {isCategory = true;}
        if (minPrice == null) {minPrice = 0.00;}
        if (maxPrice == null) {maxPrice = Double.MAX_VALUE;}
        if (sort == null) {sort = "Name";}
        return productRepository.findProductsByFilter(isCategory,category, minPrice, maxPrice, !isDiscount,  sort);
    }
}