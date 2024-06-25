package com.example.finalproject.service;


import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    public ProductResponseDto getProductById(Long id) {
        Product productResponse = productRepository.findById(id).orElse(null);
        if (productResponse != null) {
            return mappers.convertToProductResponseDto(productResponse);

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
}